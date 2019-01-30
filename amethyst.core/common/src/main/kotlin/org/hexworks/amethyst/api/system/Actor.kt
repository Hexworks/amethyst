package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context

/**
 * A [Actor] is a [System] which performs actions based on the
 * [Command]s they receive and also performs actions autonomously
 * with entities whenever they are [update]d, thus an [Actor]
 * is a combination of a [Facet] and a [Behavior].
 */
interface Actor<C : Context> : Facet<C>, Behavior<C>
