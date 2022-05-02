@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.StateResponse
import org.hexworks.amethyst.api.message.StateChanged
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

class StateMachineFacet<C : Context, P : Message<C>>(
    override val messageType: KClass<P>,
    initialState: Facet<C, out P>
) : Facet<C, P> {

    override val id: UUID
        get() = currentState.id
    override val mandatoryAttributes: Set<KClass<out Attribute>>
        get() = currentState.mandatoryAttributes

    var currentState = initialState
        private set

    override suspend fun tryReceive(message: Message<C>): Response {
        val result = currentState.tryReceive(message)
        return if (result is StateResponse<*, *, *>) {
            val oldState = currentState
            currentState = result.facet as Facet<C, P>
            MessageResponse(
                StateChanged(
                    context = message.context,
                    source = message.source,
                    oldState = oldState,
                    newState = currentState
                )
            )
        } else Pass
    }

    override suspend fun receive(message: P) = tryReceive(message)
}
