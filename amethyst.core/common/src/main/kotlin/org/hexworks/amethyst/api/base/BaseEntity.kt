package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import kotlin.reflect.KClass

abstract class BaseEntity<T : EntityType, C : Context>(
        override val type: T,
        val attributes: Set<Attribute> = setOf(),
        val facets: Set<Facet<C>> = setOf(),
        val behaviors: Set<Behavior<C>> = setOf()) : Entity<T, C> {

    override val id = IdentifierFactory.randomIdentifier()

    override fun fetchAttributes(): Set<Attribute> {
        return attributes.toSet()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Attribute> attribute(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(attributes.firstOrNull { klass.isInstance(it) } as? T)
    }

    override fun sendCommand(command: Command<out EntityType, C>): Boolean {
        return false
    }

    override fun executeCommand(command: Command<out EntityType, C>): Response {
        return Pass
    }

    override fun update(context: C): Boolean {
        return false
    }
}
