package org.hexworks.amethyst.api.mutator

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.extensions.FacetWithContext
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.accessor.DefaultFacetMutator
import kotlin.jvm.JvmStatic

@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface FacetMutator<C : Context> : FacetAccessor<C> {

    /**
     * Adds the given [Facet] to this [Entity].
     */
    fun addFacet(facet: FacetWithContext<C>)

    /**
     * Removes the given [Facet] from this [Entity].
     */
    fun removeFacet(facet: FacetWithContext<C>)

    companion object {

        @JvmStatic
        fun <C : Context> create(
            facets: Set<FacetWithContext<C>>
        ): FacetMutator<C> = DefaultFacetMutator(facets)
    }
}
