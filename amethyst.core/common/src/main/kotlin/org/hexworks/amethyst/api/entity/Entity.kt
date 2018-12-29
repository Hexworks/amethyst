package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.system.System
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

/**
 * An [Entity] is a game object composed of [Attribute] and
 * [System] objects representing a cohesive whole.
 * For example a Goblin entity can be composed of
 * `CombatHandler`, `ArmorUser`, `HunterSeeker` components
 * with a `Creature` property.
 */
interface Entity<out T : EntityType, U : Context> {

    val id: Identifier

    val type: T

    val name: String
        get() = type.name

    val description: String
        get() = type.description

    fun fetchAttributes(): Set<Attribute>

    fun <T : Attribute> attribute(klass: KClass<T>): Maybe<T>

    /**
     * Adds the given [Command] to this [Entity] for processing.
     * It will be processed when the [Entity] is updated next.
     * @return true if the [Command] can be processed by a [System] false if not.
     */
    fun sendCommand(command: Command<out EntityType, U>): Boolean

    /**
     * Makes this [Entity] immediately process this [Command].
     * @return true if the [Command] was processed by a [System] false if not.
     */
    fun executeCommand(command: Command<out EntityType, U>): Boolean

    /**
     * Updates this [Entity] using the given [context].
     * @return true if the [Entity] was updated, false if not.
     */
    fun update(context: U): Boolean

}
