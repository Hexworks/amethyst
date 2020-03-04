package org.hexworks.amethyst.api.extensions

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType

inline fun <C : Context, reified T : Command<out EntityType, C>> Command<out EntityType, C>.responseWhenCommandIs(
        noinline fn: (T) -> Response): Response {
    return responseWhenCommandIs(T::class, fn)
}

inline fun <C : Context, reified T : Command<out EntityType, C>> Command<out EntityType, C>.responseWhenCommandIs(
        noinline fn: (T) -> Response,
        noinline otherwise: () -> Response): Response {
    return responseWhenCommandIs(T::class, fn, otherwise)
}

inline fun <C : Context, reified T : Command<out EntityType, C>> Command<out EntityType, C>.whenCommandIs(
        noinline fn: (T) -> Boolean): Boolean {
    return whenCommandIs(T::class, fn)
}

inline fun <C : Context, reified T : Command<out EntityType, C>> Command<out EntityType, C>.whenCommandIs(
        noinline fn: (T) -> Boolean,
        noinline otherwise: () -> Boolean): Boolean {
    return whenCommandIs(T::class, fn, otherwise)
}
