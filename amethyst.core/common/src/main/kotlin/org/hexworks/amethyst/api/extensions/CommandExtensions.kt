package org.hexworks.amethyst.api.extensions

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.EntityType

inline fun <C : Context, reified T : Command<out EntityType, C>> Command<out EntityType, C>.whenCommandIs(
        noinline fn: (T) -> Unit): Boolean {
    return whenCommandIs(T::class, fn)
}

inline fun <C : Context, reified T : Command<out EntityType, C>, U : Any> Command<out EntityType, C>.whenCommandIs(
        noinline fn: (T) -> U,
        noinline otherwise: () -> U): U {
    return whenCommandIs(T::class, fn, otherwise)
}
