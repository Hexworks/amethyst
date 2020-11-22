package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.internal.system.CompositeFacet
import kotlin.reflect.KClass

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
interface Facet<C : Context, P : Command<C>> : System<C> {

    /**
     * The type of the [Command] this [Facet] accepts
     */
    val commandType: KClass<P>

    /**
     * Performs the given [command].
     * @see Response
     */
    suspend fun executeCommand(command: P): Response

    /**
     * Tries to execute the given [command] by checking if it has
     * an acceptable type ([commandType]).
     * @return the result of executing the [Command] or [Pass] if it
     * couldn't be accepted.
     */
    suspend fun tryExecuteCommand(command: Command<C>): Response

    /**
     * Composes this [Facet] with [other] which means that when [executeCommand]
     * is called its result will be passed to [other]'s [executeCommand] when
     * the [Response] is [Pass]
     */
    suspend fun compose(
            other: Facet<C, P>,
            commonAncestor: KClass<P>
    ): Facet<C, P> = CompositeFacet(
            children = setOf(this, other),
            commandType = commonAncestor
    )

}
