package org.hexworks.amethyst.api

import org.hexworks.amethyst.internal.DefaultEngine

object Engines {

    /**
     * Creates a new [Engine].
     */
    fun <T : Context> newEngine(): Engine<T> = DefaultEngine()
}
