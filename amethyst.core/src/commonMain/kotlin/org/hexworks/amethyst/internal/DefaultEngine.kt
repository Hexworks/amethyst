@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Synchronized

class DefaultEngine<T : Context>(
        override val coroutineContext: CoroutineContext = Dispatchers.Default
) : Engine<T>, CoroutineScope {

    private val entities = mutableListOf<Entity<EntityType, T>>()

    @Synchronized
    override fun update(context: T): Job {
        return launch {
            entities.filter { it.needsUpdate }.map {
                async { it.update(context) }
            }.awaitAll()
        }
    }

    @Synchronized
    override fun addEntity(entity: Entity<out EntityType, T>) {
        entities.add(entity as Entity<EntityType, T>)
    }

    @Synchronized
    override fun removeEntity(entity: Entity<out EntityType, T>) {
        entities.remove(entity)
    }

}
