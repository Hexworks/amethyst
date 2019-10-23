package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.EntityType

/**
 * Represents the possible [Response]s when executing a [Command].
 */
sealed class Response

/**
 * Use [Consumed] to indicate that the [Command] was handled.
 */
object Consumed : Response()

/**
 * Use [Pass] to indicate that the [Command] was not handled.
 */
object Pass : Response()

/**
 * Use [CommandResponse] to transform the given [Command] or return a new one for downstream systems.
 */
data class CommandResponse<C : Context>(val command: Command<out EntityType, C>) : Response()
