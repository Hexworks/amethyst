package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.amethyst.api.entity.Entity
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
    private val logger = LoggerFactory.getLogger(Entity::class)

    override fun sendCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Receiving command '$command' on entity '$this'.")
        eventStack.add(command)
        return false
    }

    @Suppress("UNCHECKED_CAST")
    override fun executeCommand(command: Command<out EntityType, C>): Response {
        logger.debug("Executing entity command '$command' on entity $this.")
        return if (hasFacets) {
            val iter = facets.iterator()
            var response: Response = Pass
            var lastCommand = command
            while (iter.hasNext() && response != Consumed) {
                response = iter.next().executeCommand(lastCommand)
                if (response is CommandResponse<*>) {
                    lastCommand = response.command as Command<out EntityType, C>
                }
            }
            response
        } else {
            Pass
        }
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
