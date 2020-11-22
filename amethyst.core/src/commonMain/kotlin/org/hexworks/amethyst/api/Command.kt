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
interface Command<C : Context> {

    /**
     * The system context.
     */
    val context: C

    /**
     * The [Entity] that sent this [Command].
     */
    val source: Entity<out EntityType, C>

}

