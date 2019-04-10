package org.hexworks.amethyst.api.builder

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.entity.DefaultEntity
import kotlin.reflect.KClass

/**
 * A builder for creating [Entity] objects.
 */
class EntityBuilder<T : EntityType, C : Context>(private val type: T) {

    private var attributes = setOf<Attribute>()
    private var facets = setOf<Facet<C>>()
    private var behaviors = setOf<Behavior<C>>()

    /**
     * Sets the supplied [attributes] to this builder.
     * Any previous values are overwritten.
     */
    fun attributes(vararg attributes: Attribute) = also {
        this.attributes = attributes.toSet()
    }

    /**
     * Sets the supplied [facets] to this builder.
     * Any previous values are overwritten.
     */
    fun facets(vararg facets: Facet<C>) = also {
        this.facets = facets.toSet()
    }

    /**
     * Sets the supplied [behaviors] to this builder.
     * Any previous values are overwritten.
     */
    fun behaviors(vararg behaviors: Behavior<C>) = also {
        this.behaviors = behaviors.toSet()
    }

    /**
     * Builds a new [Entity] from the contents of this builder.
     * The builder can be re-used to build multiple entities.
     */
    fun build(): Entity<T, C> {
        val finalAttributes = attributes.toSet()
        val finalBehaviors = behaviors.toSet()
        val finalFacets = facets.toSet()
        val missingAttributes = finalFacets.flatMap { it.mandatoryAttributes }
                .plus(finalBehaviors.flatMap { it.mandatoryAttributes })
                .toSet()
                .subtract(this.attributes.map { it::class })
        require(missingAttributes.isEmpty()) {
            "Can't create Entity because there are missing attributes: ${missingAttributes.joinToString { it.simpleName!! }}."
        }
        return DefaultEntity(
                type = type,
                attributes = finalAttributes,
                facets = finalFacets,
                behaviors = finalBehaviors)
    }

}

