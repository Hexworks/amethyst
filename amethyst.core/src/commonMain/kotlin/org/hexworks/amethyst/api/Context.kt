package org.hexworks.amethyst.api

import org.hexworks.amethyst.api.entity.Entity

/**
 * A [Context] object holds context information for a given [Entity]. This can be anything that is
 * relevant for the users of Amethyst and necessary for the operation of entities.
 *
 * This means that you can put arbitrary data into a [Context], like turn count, time elapsed since
 * the last update, etc.
 *
 * See: [Context Design Pattern](https://stackoverflow.com/questions/986865/can-you-explain-the-context-design-pattern).
 */
interface Context
