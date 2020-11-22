@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

abstract class BaseFacet<C : Context, P : Command<C>>(
        override val commandType: KClass<P>,
        vararg mandatoryAttribute: KClass<out Attribute>
) : Facet<C, P> {
    override val id = UUID.randomUUID()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()

    override suspend fun tryExecuteCommand(command: Command<C>): Response {
        return if (command::class == commandType) {
            executeCommand(command as P)
        } else Pass
    }
}
