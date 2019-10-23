package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.accessor.AttributeAccessor
import org.hexworks.amethyst.api.accessor.BehaviorAccessor
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

abstract class BaseEntity<T : EntityType, C : Context>(
        override val type: T,
        attributes: Set<Attribute> = setOf(),
        facets: Set<Facet<C>> = setOf(),
        behaviors: Set<Behavior<C>> = setOf()) : Entity<T, C>,
        AttributeAccessor by AttributeAccessor.create(attributes),
        FacetAccessor<C> by FacetAccessor.create(facets),
        BehaviorAccessor<C> by BehaviorAccessor.create(behaviors) {

    override val id = IdentifierFactory.randomIdentifier()

    override fun sendCommand(command: Command<out EntityType, C>): Boolean {
        return false
    }

    override fun executeCommand(command: Command<out EntityType, C>): Response {
        return Pass
    }

    override fun update(context: C): Boolean {
        return false
    }

    override fun toString(): String {
        return "BaseEntity(id=$id, type=$type, name=$name, description=$description)"
    }


}
