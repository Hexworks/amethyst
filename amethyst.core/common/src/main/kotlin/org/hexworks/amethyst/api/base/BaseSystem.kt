package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.*
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import kotlin.reflect.KClass

abstract class BaseSystem<C : Context>(vararg mandatoryAttribute: KClass<out Attribute>) : System<C> {

    override val id = IdentifierFactory.randomIdentifier()
    override val mandatoryAttributes: Set<KClass<out Attribute>> = mandatoryAttribute.toSet()

    override fun executeCommand(command: Command<out EntityType, C>): Boolean {
        return false
    }

    override fun update(entity: Entity<EntityType, C>, context: C): Boolean {
        return false
    }
}
