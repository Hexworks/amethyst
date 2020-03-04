package org.hexworks.amethyst.api

import kotlinx.coroutines.Dispatchers
import org.hexworks.amethyst.internal.DefaultEngine
import kotlin.coroutines.CoroutineContext

object Engines {

    /**
     * Creates a new [Engine].
     */
    fun <T : Context> newEngine(
            coroutineContext: CoroutineContext = Dispatchers.Default
    ): Engine<T> = DefaultEngine(coroutineContext)
}
