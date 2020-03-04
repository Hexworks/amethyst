package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.CommandResponse
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.logging.api.LoggerFactory

class DefaultEntity<T : EntityType, C : Context>(
        type: T,
        attributes: Set<Attribute> = setOf(),
        facets: Set<Facet<C>> = setOf(),
        behaviors: Set<Behavior<C>> = setOf()
) : BaseEntity<T, C>(
        type = type,
        attributes = attributes.plus(type),
        facets = facets,
        behaviors = behaviors) {

    private val eventStack = mutableListOf<Command<out EntityType, C>>()
    private val logger = LoggerFactory.getLogger(Entity::class)

    override val needsUpdate: Boolean
        get() = hasBehaviors || eventStack.isNotEmpty()

    override suspend fun sendCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Receiving command '$command' on entity '$this'.")
        eventStack.add(command)
        return false
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun executeCommand(command: Command<out EntityType, C>): Response {
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

    override suspend fun update(context: C): Boolean {
        val events = eventStack.toList()
        eventStack.clear()
        events.forEach {
            executeCommand(it)
        }
        return behaviors.fold(false) { result, behavior ->
            result or behavior.update(this, context)
        }
    }

    override fun toString() = type.name
}
