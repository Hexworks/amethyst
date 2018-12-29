package org.hexworks.amethyst.api.builder

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.entity.DefaultEntity

class EntityBuilder<T : EntityType, U : Context>(private val type: T) {

    private var attributes = setOf<Attribute>()
    private var facets = setOf<Facet<U>>()
    private var behaviors = setOf<Behavior<U>>()

    fun attributes(vararg attributes: Attribute) = also {
        this.attributes = attributes.toSet()
    }

    fun facets(vararg facets: Facet<U>) = also {
        this.facets = facets.toSet()
    }

    fun behaviors(vararg behaviors: Behavior<U>) = also {
        this.behaviors = behaviors.toSet()
    }

    fun build(): Entity<T, U> = DefaultEntity(
            type = type,
            attributes = attributes.toSet(),
            facets = facets.toSet(),
            behaviors = behaviors.toSet())

}
