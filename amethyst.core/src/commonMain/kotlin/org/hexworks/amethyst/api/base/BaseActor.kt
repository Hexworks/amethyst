package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

/**
 * Base class that can be used to create custom actor implementations.
 */
abstract class BaseActor<C : Context, P : Message<C>>(
    messageType: KClass<P>,
    vararg mandatoryAttribute: KClass<out Attribute>
) : BaseFacet<C, P>(messageType), Behavior<C> {
    override val id = UUID.randomUUID()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()
}
