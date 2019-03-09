package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.logging.api.LoggerFactory

class DefaultEngine<T : Context> : Engine<T> {

    private val logger = LoggerFactory.getLogger(Engine::class)

    private val entities = linkedMapOf<Identifier, Entity<EntityType, T>>()

    override fun update(context: T) {
        logger.debug("Updating entities using context: $context.")
        entities.values.toList().forEach {
            logger.debug("Updating entity: $it.")
            it.update(context)
        }
    }

    override fun addEntity(entity: Entity<EntityType, T>) {
        logger.debug("Adding entity $entity to engine.")
        entities[entity.id] = entity
    }

    override fun removeEntity(entity: Entity<EntityType, T>) {
        logger.debug("Removing entity $entity from engine.")
        entities.remove(entity.id)
    }
}
