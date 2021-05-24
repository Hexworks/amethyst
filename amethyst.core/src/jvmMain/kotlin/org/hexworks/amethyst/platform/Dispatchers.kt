package org.hexworks.amethyst.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

actual object Dispatchers {
    /**
     * A single-threaded dispatcher.
     */
    actual val Single: CoroutineDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
}
