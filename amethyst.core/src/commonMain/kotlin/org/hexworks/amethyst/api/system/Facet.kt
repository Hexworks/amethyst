package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.internal.system.CompositeFacet
import org.hexworks.amethyst.internal.system.StateMachineFacet
import kotlin.reflect.KClass

/**
 * A [Facet] is a [System] that performs actions based on the [Message] they receive.
 * Each [Facet] only accepts a single type of [Message] of type [M]. This enforces
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
interface Facet<C : Context, M : Message<C>> : System<C> {

    /**
     * The type of the [Message] this [Facet] accepts.
     */
    val messageType: KClass<M>

    /**
     * Receives the given [message].
     * @see Response
     */
    suspend fun receive(message: M): Response

    /**
     * Tries to receive the given [message] by checking if it has
     * an acceptable [messageType].
     * @return the result of receiving the [Message] or [Pass] if it
     * couldn't be accepted.
     */
    suspend fun tryReceive(message: Message<C>): Response

    /**
     * Composes this [Facet] with [other] which means that when [receive]
     * is called its result will be passed to [other]'s [receive] when
     * the [Response] is [Pass]
     */
    fun compose(
            other: Facet<C, M>,
            commonAncestor: KClass<M>
    ): Facet<C, M> = CompositeFacet(
            children = setOf(this, other),
            messageType = commonAncestor
    )

}
