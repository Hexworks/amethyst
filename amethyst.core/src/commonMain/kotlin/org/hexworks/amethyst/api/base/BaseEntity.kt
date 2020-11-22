package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.entity.MutableEntity
import org.hexworks.amethyst.api.mutator.AttributeMutator
import org.hexworks.amethyst.api.mutator.BehaviorMutator
import org.hexworks.amethyst.api.mutator.FacetMutator
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID

/**
 * Base class that can be used to create custom [MutableEntity] implementations.
 */
abstract class BaseEntity<T : EntityType, C : Context>(
        override val type: T,
        attributes: Set<Attribute> = setOf(),
        facets: Set<Facet<C>> = setOf(),
        behaviors: Set<Behavior<C>> = setOf()
) : MutableEntity<T, C>,
        AttributeMutator by AttributeMutator.create(attributes),
        FacetMutator<C> by FacetMutator.create(facets),
        BehaviorMutator<C> by BehaviorMutator.create(behaviors) {

    override val id = UUID.randomUUID()

    override fun asMutableEntity(): MutableEntity<T, C> {
        return this
    }

    override fun toString(): String {
        return "Entity(id=$id, type=$type, name=$name, description=$description)"
    }


}
