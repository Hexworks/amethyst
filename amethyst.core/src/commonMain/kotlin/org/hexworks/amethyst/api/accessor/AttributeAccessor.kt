package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.mutator.AttributeMutator
import org.hexworks.amethyst.internal.accessor.DefaultAttributeMutator
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

interface AttributeAccessor {

    /**
     * A [Sequence] of the current [Attribute]s of this [Entity].
     */
    val attributes: Sequence<Attribute>

    /**
     * Tells whether this [AttributeMutator] has [Attribute]s or not.
     */
    val hasAttributes: Boolean

    /**
     * Returns the [Attribute] of the given class [T] or throws an exception if the
     * attribute cannot be found.
     */
    fun <T : Attribute> findAttribute(klass: KClass<T>) =
        findAttributeOrNull(klass) ?: error("Cannot find attribute using class: $klass")

    /**
     * Returns the [Attribute] of the given class [T] (if any).
     */
    fun <T : Attribute> findAttributeOrNull(klass: KClass<T>): T?

    companion object {

        @JvmStatic
        fun create(attributes: Set<Attribute>): AttributeAccessor = DefaultAttributeMutator(attributes)
    }
}
