package org.hexworks.amethyst.internal.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class DefaultFacetAccessor<C : Context>(facets: Set<Facet<C>>) : FacetAccessor<C> {

    override val facets: Sequence<Facet<C>>
        get() = currentFacets.toSet().asSequence()

    override val hasFacets: Boolean
        get() = currentFacets.isNotEmpty()

    private val currentFacets = facets.toMutableSet()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Facet<C>> findFacet(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(currentFacets.firstOrNull { klass.isInstance(it) } as? T)
    }

    override fun addFacet(facet: Facet<C>) {
        currentFacets.add(facet)
    }

    override fun removeFacet(facet: Facet<C>) {
        currentFacets.remove(facet)
    }
}
