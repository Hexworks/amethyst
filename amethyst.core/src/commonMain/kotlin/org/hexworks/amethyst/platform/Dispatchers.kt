package org.hexworks.amethyst.platform

import kotlinx.coroutines.CoroutineDispatcher

expect object Dispatchers {
    /**
     * A single-threaded dispatcher.
     */
    val Single: CoroutineDispatcher
}
