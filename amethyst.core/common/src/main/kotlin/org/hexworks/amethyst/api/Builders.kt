package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.DefaultEngine

/**
 * Creates and sets up a new [Entity] of type [T].
 */
fun <T : EntityType, U : Context> newEntityOfType(type: T, init: EntityBuilder<T, U>.() -> Unit): Entity<T, U> {
    val builder = EntityBuilder<T, U>(type)
    init(builder)
    return builder.build()
}

/**
 * Creates a new [Engine].
 */
fun <T : Context> newEngine(): Engine<T> = DefaultEngine()
