package org.hexworks.amethyst.internal.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.FacetAccessor
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.extensions.FacetWithContext
import org.hexworks.amethyst.api.mutator.FacetMutator
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class DefaultFacetMutator<C : Context>(facets: Set<FacetWithContext<C>>) : FacetMutator<C> {

    override val facets: Sequence<FacetWithContext<C>>
        get() = currentFacets.toSet().asSequence()

    override val hasFacets: Boolean
        get() = currentFacets.isNotEmpty()

    private val currentFacets = facets.toMutableSet()

    override fun <T : FacetWithContext<C>> findFacet(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(findFacetOrNull(klass))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : FacetWithContext<C>> findFacetOrNull(klass: KClass<T>): T? {
        return currentFacets.firstOrNull { klass.isInstance(it) } as? T
    }

    override fun addFacet(facet: FacetWithContext<C>) {
        currentFacets.add(facet)
    }

    override fun removeFacet(facet: FacetWithContext<C>) {
        currentFacets.remove(facet)
    }

}
