package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.cobalt.logging.api.LoggerFactory

class DefaultEntity<T : EntityType, C : Context>(type: T,
                                                 attributes: Set<Attribute> = setOf(),
                                                 systems: Set<System<C>> = setOf())
    : BaseEntity<T, C>(
        type = type,
        attributes = attributes.plus(type),
        systems = systems) {

    private val eventStack = mutableListOf<Command<out EntityType, C>>()
    private val logger = LoggerFactory.getLogger(this::class)

    override fun sendCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Receiving command '$command' on entity '$this'.")
        eventStack.add(command)
        return false
    }

    override fun executeCommand(command: Command<out EntityType, C>): Boolean {
        logger.debug("Executing entity command '$command' on entity $this.")
        return systems.map {
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
        return systems.fold(false) { result, system: System<C> ->
            result or system.update(this, context)
        }
    }

    override fun toString() = type.name
}
