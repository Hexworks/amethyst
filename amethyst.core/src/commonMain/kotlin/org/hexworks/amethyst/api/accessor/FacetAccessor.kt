package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.extensions.FacetWithContext
import org.hexworks.amethyst.api.mutator.FacetMutator
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.accessor.DefaultFacetMutator
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface FacetAccessor<C : Context> {

    /**
     * A [Sequence] of the current [Facet]s of this [Entity].
     */
    val facets: Sequence<FacetWithContext<C>>

    /**
     * Tells whether this [FacetMutator] has [Facet]s or not.
     */
    val hasFacets: Boolean

    /**
     * Returns the [Facet] of the given class [T] or throws an exception if the
     * facet cannot be found.
     */
    fun <T : FacetWithContext<C>> findFacet(klass: KClass<T>) =
        findFacetOrNull(klass) ?: error("Cannot find facet using class: $klass")

    /**
     * Returns the [Facet] of the given class [T] (if any).
     */
    fun <T : FacetWithContext<C>> findFacetOrNull(klass: KClass<T>): T?

    companion object {

        @JvmStatic
        fun <C : Context> create(facets: Set<FacetWithContext<C>>): FacetAccessor<C> = DefaultFacetMutator(facets)
    }
}
