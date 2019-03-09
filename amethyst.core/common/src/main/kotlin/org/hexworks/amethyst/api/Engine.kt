package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.DefaultEngine

interface Engine<T : Context> {

    /**
     * Adds the given [Entity] to this [Engine].
     */
    fun addEntity(entity: Entity<EntityType, T>)

    /**
     * Removes the given [Entity] from this [Engine].
     */
    fun removeEntity(entity: Entity<EntityType, T>)

    /**
     * Updates the [Entity] objects in this [Engine] with the given [context].
     */
    fun update(context: T)

    companion object {

        fun <T : Context> default() = DefaultEngine<T>()
    }
}
