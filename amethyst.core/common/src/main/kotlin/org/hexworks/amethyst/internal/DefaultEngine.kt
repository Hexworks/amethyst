package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Identifier

class DefaultEngine<T : Context> : Engine<T> {

    private val entities = linkedMapOf<Identifier, Entity<EntityType, T>>()

    override fun update(context: T) {
        entities.values.toList().forEach {
            it.update(context)
        }
    }

    override fun addEntity(entity: Entity<EntityType, T>) {
        entities[entity.id] = entity
    }

    override fun removeEntity(entity: Entity<EntityType, T>) {
        entities.remove(entity.id)
    }
}
