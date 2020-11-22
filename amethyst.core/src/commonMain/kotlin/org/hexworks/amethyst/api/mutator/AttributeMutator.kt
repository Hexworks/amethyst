package org.hexworks.amethyst.api.mutator

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.accessor.AttributeAccessor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.internal.accessor.DefaultAttributeMutator
import kotlin.jvm.JvmStatic

interface AttributeMutator : AttributeAccessor {

    /**
     * Adds the given [Attribute] to this [Entity].
     */
    fun addAttribute(attribute: Attribute)

    /**
     * Removes the given [Attribute] from this [Entity].
     */
    fun removeAttribute(attribute: Attribute)

    companion object {

        @JvmStatic
        fun create(attributes: Set<Attribute>): AttributeMutator = DefaultAttributeMutator(attributes)
    }
}
