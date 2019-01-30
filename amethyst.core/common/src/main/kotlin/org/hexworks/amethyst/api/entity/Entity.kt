package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.accessor.AttributeAccessor
import org.hexworks.amethyst.api.accessor.BehaviorAccessor
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.system.System
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

/**
 * An [Entity] is a game object composed of [Attribute] and [System] objects
 * representing a cohesive whole. For example a Goblin entity can be composed
 * of `CombatHandler`, `ArmorUser` and `HunterSeeker` [System]s with a
 * `Creature` [Attribute] to represent how a Goblin works.
 */
interface Entity<out T : EntityType, C : Context> : AttributeAccessor, FacetAccessor<C>, BehaviorAccessor<C> {

    /**
     * The unique [Identifier] of this [Entity].
     */
    val id: Identifier

    /**
     * Represents the [EntityType] of this [Entity].
     * An [EntityType] is a mandatory [Attribute] of each
     * [Entity] which describes the given [Entity].
     */
    val type: T

    /**
     * Shorthand for accessing [EntityType.name].
     */
    val name: String
        get() = type.name

    /**
     * Shorthand for accessing [EntityType.description].
     */
    val description: String
        get() = type.description

    /**
     * Adds the given [Command] to this [Entity] for processing.
     * It will be processed when the [Entity] is updated next.
     * @return true if the [Command] can be processed by a [System] false if not.
     */
    fun sendCommand(command: Command<out EntityType, C>): Boolean

    /**
     * Makes this [Entity] immediately process this [Command].
     * @return the [Response] for the given [command]
     * @see [Response] for more info.
     */
    fun executeCommand(command: Command<out EntityType, C>): Response

    /**
     * Updates this [Entity] using the given [context].
     * See [Context] for more info about what a [context] is supposed to be.
     * @return true if the [Entity] was updated, false if not.
     */
    fun update(context: C): Boolean

}
