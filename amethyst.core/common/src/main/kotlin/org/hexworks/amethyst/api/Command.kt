package org.hexworks.amethyst.api

import kotlin.reflect.KClass

interface Command<T : EntityType, C : Context> {

    val context: C
    /**
     * The [Entity] which is the source of this [Command].
     */
    val source: Entity<T, C>

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>, U : Any> whenCommandIs(
            klass: KClass<T>,
            fn: (T) -> U,
            otherwise: () -> U): U {
        return if (klass.isInstance(this)) {
            fn(this as T)
        } else {
            otherwise()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Command<out EntityType, C>> whenCommandIs(klass: KClass<T>,
                                                       fn: (T) -> Unit): Boolean {
        return if (klass.isInstance(this)) {
            fn(this as T)
            true
        } else {
            false
        }
    }
}

