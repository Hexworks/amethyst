package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.system.CompositeAndFacet
import org.hexworks.amethyst.internal.system.CompositeLinkFacet
import org.hexworks.amethyst.internal.system.CompositeOrFacet

/**
 * A [Facet] is a [System] which performs actions based on the
 * [Command]s they receive.
 */
interface Facet<C : Context> : System<C> {

    /**
     * Performs the given [Command].
     * @return `true` if the given [command] interacted with this [Facet],
     * `false` otherwise.
     */
    fun executeCommand(command: Command<out EntityType, C>): Boolean

    /**
     * Performs an or combination of this [Facet] and the [other] [Facet]:
     * When [executeCommand] is called it is run on this one first, and [other]'s [executeCommand]
     * is only run if this [executeCommand] returns `false`.
     * @return their results `or`-ed together (if any of them is a success, their
     * combined result is also a success).
     */
    infix fun or(other: Facet<C>): Facet<C> {
        return CompositeOrFacet(this, other)
    }

    /**
     * Performs an and combination of this [Facet] and the [other] [Facet]:
     * When [executeCommand] is called it is run on this one first, and then on [other].
     * @return their results `and`-ed together (it is only a success if both updates
     * are successes).
     */
    infix fun and(other: Facet<C>): Facet<C> {
        return CompositeAndFacet(this, other)
    }

    /**
     * Links this and the [other] [Facet]:
     * When [executeCommand] is called it is run on this one first, and then on [other].
     * @return their results `or`-ed together (if any of them is a success, their
     * combined result is also a success).
     */
    infix fun link(other: Facet<C>): Facet<C> {
        return CompositeLinkFacet(this, other)
    }
}
