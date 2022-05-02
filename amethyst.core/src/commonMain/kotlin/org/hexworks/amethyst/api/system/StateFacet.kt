package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.StateResponse

import org.hexworks.amethyst.internal.system.StateMachineFacet

/**
 * A [StateFacet] is a [Facet] that can be used by a [StateMachineFacet]
 * to represent a particular state the state machine is in.
 */
interface StateFacet<C : Context, M : Message<C>> : Facet<C, M> {

    /**
     * If a [Facet] is part used in a [StateMachineFacet] and it returns
     * a [StateResponse] when receiving the given [message] [onEnter]
     * will be called on the [StateFacet] that's returned.
     */
    suspend fun onEnter(message: M) {}

    /**
     * If a [Facet] is part used in a [StateMachineFacet] and it returns
     * a [StateResponse] when receiving the given [message] [onExit]
     * will be called on the [StateFacet] that's being replaced.
     */
    suspend fun onExit(message: M) {}
}