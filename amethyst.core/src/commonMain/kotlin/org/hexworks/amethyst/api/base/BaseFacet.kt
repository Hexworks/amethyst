@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.message.StateChanged
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

abstract class BaseFacet<C : Context, P : Message<C>>(
        override val messageType: KClass<P>,
        vararg mandatoryAttribute: KClass<out Attribute>
) : Facet<C, P> {
    override val id = UUID.randomUUID()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()

    override suspend fun tryReceive(message: Message<C>): Response {
        return if (message::class == messageType) {
            receive(message as P)
        } else Pass
    }
}
