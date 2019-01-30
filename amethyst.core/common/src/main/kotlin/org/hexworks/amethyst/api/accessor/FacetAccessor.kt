package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.accessor.DefaultFacetAccessor
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface FacetAccessor<C : Context> {

    /**
     * A [Sequence] of the current [Facet]s of this [Entity].
     */
    val facets: Sequence<Facet<C>>

    /**
     * Tells whether this [FacetAccessor] has [Facet]s or not.
     */
    val hasFacets: Boolean

    /**
     * Returns the [Facet] of the given class [T] (if any).
     */
    fun <T : Facet<C>> findFacet(klass: KClass<T>): Maybe<T>

    /**
     * Adds the given [Facet] to this [Entity].
     */
    fun addFacet(facet: Facet<C>)

    /**
     * Removes the given [Facet] from this [Entity].
     */
    fun removeFacet(facet: Facet<C>)

    companion object {

        fun <C : Context> create(facets: Set<Facet<C>>): FacetAccessor<C> = DefaultFacetAccessor(facets)
    }
}
