package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

interface Command<T : EntityType, C : Context> {

    val context: C
    /**
     * The [Entity] which is the source of this [Command].
     */
    val source: Entity<T, C>

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> whenCommandIs(
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
    fun <T : Command<out EntityType, C>> whenCommandIs(klass: KClass<T>,
                                                       fn: (T) -> Response): Response {
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
            otherwise: () -> Boolean): Boolean {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            otherwise()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> whenCommandIs(klass: KClass<T>,
                                                       fn: (T) -> Boolean): Boolean {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            false
        }
    }
}

