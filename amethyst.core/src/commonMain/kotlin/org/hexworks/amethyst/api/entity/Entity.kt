package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.accessor.AttributeAccessor
import org.hexworks.amethyst.api.accessor.BehaviorAccessor
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.system.System
import org.hexworks.cobalt.core.api.UUID

/**
 * An [Entity] is an object composed of [Attribute]s and [System]s representing a cohesive whole.
 * For example a Goblin entity can be composed of `CombatHandler`, `ArmorUser` and `HunterSeeker`
 * [System]s with a `Creature` [Attribute] to represent how a Goblin works.
 */
interface Entity<T : EntityType, C : Context> : AttributeAccessor, FacetAccessor<C>, BehaviorAccessor<C> {

    /**
     * The unique [UUID] of this [Entity].
     */
    val id: UUID

    /**
     * Tells whether this [Entity] needs update or not. An update is needed
     * when the entity has [Behavior]s, or it has unprocessed [Message]s.
     */
    val needsUpdate: Boolean

    /**
     * Represents the [EntityType] of this [Entity]. An [EntityType] is a mandatory [Attribute]
     * of every [Entity].
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
     * Adds the given [Message] to this [Entity] for processing. It will be processed when the [Entity]
     * is updated next.
     * @return true if the [Message] can be processed by a [System] false if not.
     */
    suspend fun sendMessage(message: Message<C>): Boolean

    /**
     * Makes this [Entity] immediately process this [Message].
     * @return the [Response] for the given [message]
     * @see [Response] for more info.
     */
    suspend fun receiveMessage(message: Message<C>): Response

    /**
     * Updates this [Entity] using the given [context]. See [Context] for more info about what a [context]
     * is supposed to be.
     * @return true if the [Entity] was updated, false if not.
     */
    suspend fun update(context: C): Boolean

    /**
     * Returns a mutable version of this [Entity]. The underlying [Entity] will be the same object.
     */
    fun asMutableEntity(): MutableEntity<T, C>
}
