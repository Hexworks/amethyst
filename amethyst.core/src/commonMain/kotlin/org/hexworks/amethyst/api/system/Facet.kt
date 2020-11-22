package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.system.CompositeFacet

/**
 * A [Facet] is a [System] that performs actions based on the [Command] they receive.
 * Each [Facet] only accepts a single type of [Command] of type [P]. This enforces
 * that facets adhere to the *Single Responsibility Principle*.
 *
 * A [Facet] should not have internal mutable state, instead it should behave as
 * a regular function. For mutable state, [Attribute]s should be used.
 *
 * [Facet]s are typically triggered when [Behavior]s are [Behavior.update]d.
 *
 * [Facet]s can be composed TODO
 *
 * @see Attribute
 */
interface Facet<T : EntityType, C : Context, P : Command<T, C>> : System<C> {

    /**
     * Performs the given [command].
     * @see Response
     */
    suspend fun executeCommand(command: P): Response

    /**
     * Alias for [plus]
     * @see plus
     */
    suspend fun compose(other: Facet<T, C, P>): Facet<T, C, P> = plus(other)

    /**
     * Composes this [Facet] with [other] which means that when [executeCommand]
     * is called its result will be passed to [other]'s [executeCommand] when
     * the [Response] is [Pass]
     */
    suspend operator fun plus(other: Facet<T, C, P>): Facet<T, C, P> = CompositeFacet(
            children = setOf(this, other)
    )

}
