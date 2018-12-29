package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import kotlin.reflect.KClass

abstract class BaseBehavior<C : Context>(vararg mandatoryAttribute: KClass<out Attribute>) : Behavior<C> {

    override val id = IdentifierFactory.randomIdentifier()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()

}
