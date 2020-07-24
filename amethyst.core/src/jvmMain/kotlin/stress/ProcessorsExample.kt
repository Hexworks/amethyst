import kotlinx.coroutines.*
import org.hexworks.amethyst.api.Context
import org.hexworks.cobalt.core.api.UUID
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

interface Attribute {
    val name: String
}

interface EntityType<C : Context> : Attribute

data class Category(val value: String) : Attribute {
    override val name = "category"
}

data class DependsOn(val processors: List<ProcessorEntity>) : Attribute {
    override val name = "depends on"
}

data class ProcessorContext(
        val currentTime: Long,
        val entities: Sequence<Entity<Context, EntityType<Context>>>
) : Context

object Processor : EntityType<ProcessorContext> {
    override val name = "Processor"
}

typealias ProcessorEntity = Entity<ProcessorContext, Processor>

@Suppress("UNCHECKED_CAST")
class Engine(
        override val coroutineContext: CoroutineContext = Dispatchers.Default
) : CoroutineScope {

    private val entities = mutableMapOf<KClass<out Context>, MutableList<Entity<Context, EntityType<Context>>>>()

    fun <C : Context, T : EntityType<C>> addEntity(entity: Entity<C, T>) {
        entities.getOrPut(entity.contextClass) { mutableListOf() }.add(entity as Entity<Context, EntityType<Context>>)
    }

    fun <C : Context, T : EntityType<C>> removeEntity(entity: Entity<C, T>) {
        // ...
    }

    fun update(currentTimeMs: Long): Job {
        return launch {
            entities[ProcessorContext::class]?.map {
                async {
                    it.update(ProcessorContext(
                            currentTime = System.currentTimeMillis(),
                            entities = entities.values.asSequence().flatMap { it.asSequence() }
                    ))
                }
            }?.awaitAll()
        }
    }
}

fun Engine.addProcessor(processor: Entity<ProcessorContext, Processor>) {
    addEntity(processor)
}

interface Behavior<C : Context, T : EntityType<C>> {

    suspend fun update(entity: Entity<C, T>, context: C): Boolean

}

class Entity<C : Context, T : EntityType<C>>(
        val type: T,
        val contextClass: KClass<C>,
        val attributes: Set<Attribute>,
        val behaviors: Set<Behavior<C, T>>
) {

    val id: UUID = UUID.randomUUID()

    suspend fun update(context: C): Boolean {
        return behaviors.fold(false) { acc, next -> acc || next.update(this, context) }
    }
}

fun processor(attributes: Set<Attribute>, fn: suspend (ProcessorContext) -> Boolean) = Entity(
        type = Processor,
        contextClass = ProcessorContext::class,
        attributes = attributes,
        behaviors = setOf(object : Behavior<ProcessorContext, Processor> {
            override suspend fun update(
                    entity: Entity<ProcessorContext, Processor>,
                    context: ProcessorContext
            ): Boolean {
                return fn(context)
            }
        })
)

data class LastUpdate(var lastUpdate: Long = System.currentTimeMillis()) : Attribute {
    override val name = "last update"
}

object FloraContext : Context
object FaunaContext : Context
object CombatContext : Context

fun main() {

    val engine = Engine()

    val floraProcessor = processor(
            attributes = setOf(LastUpdate(), Category("flora"))
    ) { (currentTime, entities) ->
        // we get the last update from this entity and calculate whether we want to update now
        entities.filter {
            it.contextClass == FloraContext::class
        }.forEach {
            it.update(FloraContext as Context)
        }
        true
    }
    val faunaProcessor = processor(
            attributes = setOf(LastUpdate(), Category("fauna"), DependsOn(listOf(floraProcessor)))
    ) { (currentTime, entities) ->
        // same as above, but different filtering
        true
    }
    val combatProcessor = processor(
            attributes = setOf(LastUpdate(), Category("combat"), DependsOn(listOf(floraProcessor, faunaProcessor)))
    ) { (currentTime, entities) ->
        // same as above, but different filtering
        true
    }

    engine.addProcessor(floraProcessor)
    engine.addProcessor(faunaProcessor)
    engine.addProcessor(combatProcessor)

    // add the rest of the entities

}


