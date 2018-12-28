package org.hexworks.amethyst.api

import org.hexworks.amethyst.internal.CompositeOrSystem
import org.hexworks.cobalt.datatypes.Identifier
import kotlin.reflect.KClass

/**
 * A [System] is responsible for updating the internal state
 * ([Attribute]s) of an [Entity]
 */
interface System<C : Context> {

    val id: Identifier
    val mandatoryAttributes: Set<KClass<out Attribute>>

    fun update(entity: Entity<EntityType, C>, context: C): Boolean

    fun executeCommand(command: Command<out EntityType, C>): Boolean

    infix fun or(other: System<C>): System<C> {
        return CompositeOrSystem(listOf(this, other))
    }
}
