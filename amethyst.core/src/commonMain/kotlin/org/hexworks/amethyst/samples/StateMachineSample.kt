package org.hexworks.amethyst.samples

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.extensions.toStateMachine
import org.hexworks.amethyst.api.message.StateChanged
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

object MyContext : Context

sealed class BarrierAction : Message<MyContext>

data class Unlock(
        override val context: MyContext,
        override val source: Entity<out EntityType, MyContext>
) : BarrierAction()

data class Open(
        override val context: MyContext,
        override val source: Entity<out EntityType, MyContext>
) : BarrierAction()

data class Close(
        override val context: MyContext,
        override val source: Entity<out EntityType, MyContext>
) : BarrierAction()

object Unlockable : BaseFacet<MyContext, Unlock>(Unlock::class) {
    override suspend fun receive(message: Unlock): Response {
        return StateResponse(Openable)
    }
}

object Openable : BaseFacet<MyContext, Open>(Open::class) {
    override suspend fun receive(message: Open): Response {
        return StateResponse(Closeable)
    }
}

object Closeable : BaseFacet<MyContext, Close>(Close::class) {
    override suspend fun receive(message: Close): Response {
        return StateResponse(Openable)
    }
}

object MyType : EntityType {
    override val name = "Chest"
    override val description = "A locked chest, holding precious items"
    override val id = UUID.randomUUID()
}

data class HiddenInventory(
        val items: Set<String>
) : BaseAttribute()

data class Inventory(
        val items: Set<String>
) : BaseAttribute()

object InventoryHandler : BaseFacet<MyContext, StateChanged<MyContext>>(messageType = StateChanged::class as KClass<StateChanged<MyContext>>) {
    override suspend fun receive(message: StateChanged<MyContext>): Response {
        val entity = message.source.asMutableEntity()
        return when (message.newState) {
            is Closeable -> entity.findAttributeOrNull(HiddenInventory::class)?.let {
                entity.removeAttribute(it)
                entity.addAttribute(Inventory(it.items))
                Consumed
            } ?: Pass
            is Openable -> entity.findAttributeOrNull(Inventory::class)?.let {
                entity.removeAttribute(it)
                entity.addAttribute(HiddenInventory(it.items))
                Consumed
            } ?: Pass
            else -> Pass
        }
    }

}

fun runExample() {
    val entity = EntityBuilder.newBuilder<MyType, MyContext>(MyType)
            .attributes(HiddenInventory(setOf("gold", "silver")))
            .facets(
                    InventoryHandler,
                    Unlockable.toStateMachine(BarrierAction::class)
            )
            .build()
}
