package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

/**
 * Base class that can be used to create custom actor implementations.
 */
abstract class BaseActor<T : EntityType, C : Context, P : Command<T, C>>(
        vararg mandatoryAttribute: KClass<out Attribute>
) : Facet<T, C, P>, Behavior<C> {
    override val id = UUID.randomUUID()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()
}
