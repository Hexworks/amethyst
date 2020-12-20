package stress

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.internal.TurnBasedEngine
import org.hexworks.cobalt.core.api.UUID
import java.util.concurrent.Executors
import kotlin.reflect.KClass
import kotlin.system.measureNanoTime

@Suppress("BlockingMethodInNonBlockingContext")
object EngineStressTest {

    private val target: TurnBasedEngine<TestContext> =
        Engine.create(Executors.newFixedThreadPool(1).asCoroutineDispatcher())

    @JvmStatic
    fun main(args: Array<String>) {
        runManyEntitiesTest()
    }

    fun runManyEntitiesTest() = runBlocking {
        executeStressTestWith(1_000_000, 10000, ::createNoOpEntity)
    }

    fun runLongWorkingEntitiesTest() = runBlocking {
        executeStressTestWith(100, 500) {
            createWaitingEntity(it, 1)
        }
    }

    private suspend fun executeStressTestWith(
        entityCount: Int,
        repeats: Int,
        entityBuilder: (Int) -> Entity<TestEntityType, TestContext>
    ) {
        repeat(entityCount) {
            target.addEntity(entityBuilder(it))
        }
        var counter = 0
        var timeSumMs = 0L
        repeat(repeats) {
            val nano = measureNanoTime {
                target.executeTurn(TestContext).join()
            }
            counter++
            timeSumMs += nano / 1000 / 1000
            if (counter == 100) {
                counter = 0
                val measurement = timeSumMs / 100
                timeSumMs = 0
                println("Average update time for $entityCount entities after 100 iterations: ${measurement}ms")
            }
        }
    }

    private fun createNoOpEntity(idx: Int): Entity<TestEntityType, TestContext> {
        return newEntityOfType(TestEntityType) {
            attributes(
                TestAttribute(
                    name = "name $idx",
                    age = idx
                )
            )
            facets(TestFacet)
        }
    }

    private fun createWaitingEntity(idx: Int, waitMs: Long): Entity<TestEntityType, TestContext> {
        return newEntityOfType(TestEntityType) {
            attributes(
                TestAttribute(
                    name = "name $idx",
                    age = idx
                )
            )
            facets(TestFacet)
            behaviors(WaitingBehavior(waitMs))
        }
    }

    class WaitingBehavior<C : Context>(private val waitMs: Long) : Behavior<C> {

        override val id: UUID = UUID.randomUUID()
        override val mandatoryAttributes: Set<KClass<out Attribute>> = setOf()

        override suspend fun update(entity: Entity<EntityType, C>, context: C): Boolean {
            Thread.sleep(waitMs)
            return true
        }

    }
}
