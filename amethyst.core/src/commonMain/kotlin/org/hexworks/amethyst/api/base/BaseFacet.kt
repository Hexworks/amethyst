package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

abstract class BaseFacet<T : EntityType, C : Context, P : Command<T, C>>(
        vararg mandatoryAttribute: KClass<out Attribute>
) : Facet<T, C, P> {
    override val id = UUID.randomUUID()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()
}
