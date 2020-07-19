package org.hexworks.amethyst.internal.accessor

import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.mutator.AttributeMutator
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class DefaultAttributeMutator(attributes: Set<Attribute>) : AttributeMutator {

    override val attributes: Sequence<Attribute>
        get() = currentAttributes.values.asSequence()

    override val hasAttributes: Boolean
        get() = currentAttributes.isNotEmpty()

    private var currentAttributes = attributes.map {
        it.id to it
    }.toMap().toPersistentHashMap()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Attribute> findAttribute(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(currentAttributes.values.firstOrNull { klass.isInstance(it) } as? T)
    }

    override fun addAttribute(attribute: Attribute) {
        currentAttributes = currentAttributes.put(attribute.id, attribute)
    }

    override fun removeAttribute(attribute: Attribute) {
        currentAttributes = currentAttributes.remove(attribute.id)
    }
}
