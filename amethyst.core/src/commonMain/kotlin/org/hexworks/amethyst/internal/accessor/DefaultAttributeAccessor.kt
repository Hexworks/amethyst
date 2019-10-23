package org.hexworks.amethyst.internal.accessor

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.accessor.AttributeAccessor
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class DefaultAttributeAccessor(attributes: Set<Attribute>) : AttributeAccessor {

    override val attributes: Sequence<Attribute>
        get() = currentAttributes.toSet().asSequence()

    override val hasAttributes: Boolean
        get() = currentAttributes.isNotEmpty()

    private val currentAttributes = attributes.toMutableSet()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Attribute> findAttribute(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(currentAttributes.firstOrNull { klass.isInstance(it) } as? T)
    }

    override fun addAttribute(attribute: Attribute) {
        currentAttributes.add(attribute)
    }

    override fun removeAttribute(attribute: Attribute) {
        currentAttributes.remove(attribute)
    }
}
