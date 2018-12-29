package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.logging.api.LoggerFactory

class DefaultEntity<T : EntityType, C : Context>(type: T,
                                                 attributes: Set<Attribute> = setOf(),
                                                 facets: Set<Facet<C>> = setOf(),
                                                 behaviors: Set<Behavior<C>> = setOf())
    : BaseEntity<T, C>(
        type = type,
        attributes = attributes.plus(type),
        facets = facets,
        behaviors = behaviors) {

    private val eventStack = mutableListOf<Command<out EntityType, C>>()
    private val logger = LoggerFactory.getLogger(this::class)

    override fun sendCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Receiving command '$command' on entity '$this'.")
        eventStack.add(command)
        return false
    }

    override fun executeCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Executing entity command '$command' on entity $this.")
        return facets.map {
            it.executeCommand(command)
        }.fold(false, Boolean::or)
    }

    override fun update(context: C): Boolean {
        val events = eventStack.toList()
        eventStack.clear()
        events.forEach {
            executeCommand(it)
        }
        logger.debug("Updating entity '$this'.")
        return behaviors.fold(false) { result, behavior ->
            result or behavior.update(this, context)
        }
    }

    override fun toString() = type.name
}
