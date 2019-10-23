package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.internal.accessor.DefaultAttributeAccessor
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface AttributeAccessor {

    /**
     * A [Sequence] of the current [Attribute]s of this [Entity].
     */
    val attributes: Sequence<Attribute>

    /**
     * Tells whether this [AttributeAccessor] has [Attribute]s or not.
     */
    val hasAttributes: Boolean

    /**
     * Returns the [Attribute] of the given class [T] (if any).
     */
    fun <T : Attribute> findAttribute(klass: KClass<T>): Maybe<T>

    /**
     * Adds the given [Attribute] to this [Entity].
     */
    fun addAttribute(attribute: Attribute)

    /**
     * Removes the given [Attribute] from this [Entity].
     */
    fun removeAttribute(attribute: Attribute)

    companion object {

        fun create(attributes: Set<Attribute>): AttributeAccessor = DefaultAttributeAccessor(attributes)
    }
}
