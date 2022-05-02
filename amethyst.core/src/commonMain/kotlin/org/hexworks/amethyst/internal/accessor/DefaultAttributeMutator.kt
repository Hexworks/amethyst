package org.hexworks.amethyst.internal.accessor

import kotlinx.collections.immutable.toPersistentHashMap
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.mutator.AttributeMutator
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultAttributeMutator(attributes: Set<Attribute>) : AttributeMutator {

    override val attributes: Sequence<Attribute>
        get() = currentAttributes.values.asSequence()

    override val hasAttributes: Boolean
        get() = currentAttributes.isNotEmpty()

    private var currentAttributes = attributes.map {
        it.id to it
    }.toMap().toPersistentHashMap()

    override fun <T : Attribute> findAttributeOrNull(klass: KClass<T>): T? {
        return currentAttributes.values.firstOrNull { klass.isInstance(it) } as? T
    }

    override fun addAttribute(attribute: Attribute) {
        currentAttributes = currentAttributes.put(attribute.id, attribute)
    }

    override fun removeAttribute(attribute: Attribute) {
        currentAttributes = currentAttributes.remove(attribute.id)
    }
}
