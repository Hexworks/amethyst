package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.system.CompositeAndBehavior
import org.hexworks.amethyst.internal.system.CompositeLinkBehavior
import org.hexworks.amethyst.internal.system.CompositeOrBehavior

/**
 * A [Behavior] is a [System] which performs actions autonomously
 * with entities whenever they are [update]d.
 */
interface Behavior<C : Context> : System<C> {

    /**
     * Updates the given [entity] using the given [context].
     * @return `true` if this [Behavior] was performed successfully,`false` otherwise.
     */
    fun update(entity: Entity<EntityType, C>, context: C): Boolean

    /**
     * Performs an or combination of this [Behavior] and the [other] [Behavior]:
     * When [update] is called it is run on this one first, and [other]'s [update]
     * is only run if this [update] returns `false`.
     * @return their results `or`-ed together (if any of them is a success, their
     * combined result is also a success).
     */
    infix fun or(other: Behavior<C>): Behavior<C> {
        return CompositeOrBehavior(this, other)
    }

    /**
     * Performs an and combination of this [Behavior] and the [other] [Behavior]:
     * When [update] is called it is run on this one first, and then on [other].
     * @return their results `and`-ed together (it is only a success if both updates
     * are successes).
     */
    infix fun and(other: Behavior<C>): Behavior<C> {
        return CompositeAndBehavior(this, other)
    }

    /**
     * Links this and the [other] [Behavior]:
     * When [update] is called it is run on this one first, and then on [other].
     * @return their results `or`-ed together (if any of them is a success, their
     * combined result is also a success).
     */
    infix fun link(other: Behavior<C>): Behavior<C> {
        return CompositeLinkBehavior(this, other)
    }
}
