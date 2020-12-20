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

class RealTimeEngine<T : Context>(
        override val coroutineContext: CoroutineContext = Dispatchers.Default
) : Engine<T>, CoroutineScope {

    private val entities = mutableListOf<Entity<EntityType, T>>()

    override fun start(context: T): Job {
        TODO("We need to find a good multiplatform priority queue implementation in order to get this working")
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
