package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType

/**
 * A [Facet] is a [System] which performs actions based on the
 * [Command]s they receive.
 */
interface Facet<C : Context> : System<C> {

    /**
     * Performs the given [Command].
     * @return the [Response] to the given [command].
     * @see [Response] for more info.
     */
    fun executeCommand(command: Command<out EntityType, C>): Response

}
