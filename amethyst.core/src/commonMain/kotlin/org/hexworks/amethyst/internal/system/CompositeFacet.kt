package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
class CompositeFacet<C : Context, P : Message<C>>(
        private val children: Set<Facet<C, P>>,
        override val messageType: KClass<P>,
        override val id: UUID = UUID.randomUUID(),
        override val mandatoryAttributes: Set<KClass<out Attribute>> = children.flatMap {
            it.mandatoryAttributes
        }.toSet()
) : Facet<C, P> {

    override suspend fun receive(message: P) = tryReceive(message)

    override suspend fun tryReceive(message: Message<C>): Response {
        val iter = children.iterator()
        var response: Response = Pass
        while (iter.hasNext() && response is Pass) {
            response = iter.next().tryReceive(message)
        }
        return response
    }
}
