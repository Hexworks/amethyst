package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.extensions.toStateMachine
import org.hexworks.amethyst.api.system.Facet

/**
 * Represents the possible [Response]s when executing a [Message].
 */
sealed class Response

/**
 * Use [Consumed] to indicate that the [Message] was handled.
 */
object Consumed : Response()

/**
 * Use [Pass] to indicate that the [Message] was not handled, or was partially handled.
 */
object Pass : Response()

/**
 * Use [MessageResponse] to transform the given [Message] or return a new one for downstream systems.
 */
data class MessageResponse<C : Context>(val message: Message<C>) : Response()

/**
 * Represents a state change. Returns a new [Facet] that should be used to handle the
 * new state of a state machine.
 * @see toStateMachine
 */
data class StateResponse<C : Context, P : Message<C>, F : Facet<C, P>>(
    val facet: F
) : Response()
