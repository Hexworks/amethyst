package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.api.system.System

/**
 * A [Message] carries metadata, that's used by [Facet]s to perform their actions.
 * Since [System]s have no internal state a [Message] will encapsulate all necessary data
 * to perform [Facet.receive]
 */
interface Message<C : Context> {

    /**
     * The system context.
     */
    val context: C

    /**
     * The [Entity] that sent this [Message].
     * // TODO: shouldn't this be `self` or something similar?
     */
    val source: Entity<out EntityType, C>

}

