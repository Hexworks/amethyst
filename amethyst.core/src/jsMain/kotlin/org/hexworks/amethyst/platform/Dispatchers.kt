package org.hexworks.amethyst.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object Dispatchers {
    /**
     * A single-threaded dispatcher.
     */
    actual val Single: CoroutineDispatcher = Dispatchers.Default
}
