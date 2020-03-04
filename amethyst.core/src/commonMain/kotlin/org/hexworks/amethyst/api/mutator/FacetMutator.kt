package org.hexworks.amethyst.api.mutator

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.accessor.DefaultFacetMutator

interface FacetMutator<C : Context> : FacetAccessor<C> {

    /**
     * Adds the given [Facet] to this [Entity].
     */
    fun addFacet(facet: Facet<C>)

    /**
     * Removes the given [Facet] from this [Entity].
     */
    fun removeFacet(facet: Facet<C>)

    companion object {

        fun <C : Context> create(facets: Set<Facet<C>>): FacetMutator<C> = DefaultFacetMutator(facets)
    }
}
