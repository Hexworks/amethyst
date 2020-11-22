package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.api.system.System

/**
 * A [Command] is used by [Facet]s to perform their actions. Since [System]s
 * have no internal state a [Command] will encapsulate all necessary data
 * to perform [Facet.executeCommand]
 */
interface Command<T : EntityType, C : Context> {

    /**
     * The system context.
     */
    val context: C

    /**
     * The [Entity] that sent this [Command].
     */
    val source: Entity<T, C>

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> responseWhenCommandIs(
            klass: KClass<T>,
            fn: (T) -> Response,
            otherwise: () -> Response): Response {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            otherwise()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> responseWhenCommandIs(
            klass: KClass<T>,
            fn: (T) -> Response
    ): Response {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            Pass
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> whenCommandIs(
            klass: KClass<T>,
            fn: (T) -> Boolean,
            otherwise: () -> Boolean
    ): Boolean {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            otherwise()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> whenCommandIs(
            klass: KClass<T>,
            fn: (T) -> Boolean
    ): Boolean {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            false
        }
    }
}

