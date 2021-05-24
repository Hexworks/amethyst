@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.internal

import kotlinx.coroutines.*
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Synchronized

class TurnBasedEngine<T : Context>(
    override val coroutineContext: CoroutineContext
) : Engine<T>, CoroutineScope {

    private val entities = mutableListOf<Entity<EntityType, T>>()

    @Synchronized
    fun executeTurn(context: T): Job {
        return launch {
            entities.filter { it.needsUpdate }.map {
                async { it.update(context) }
            }.awaitAll()
        }
    }

    @Synchronized
    override fun start(context: T): Job {
        return executeTurn(context)
    }

    @Synchronized
    override fun addEntity(entity: Entity<EntityType, T>) {
        entities.add(entity)
    }

    @Synchronized
    override fun removeEntity(entity: Entity<EntityType, T>) {
        entities.remove(entity)
    }

}
