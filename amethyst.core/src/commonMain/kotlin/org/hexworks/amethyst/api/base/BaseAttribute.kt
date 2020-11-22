package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.core.api.UUID

/**
 * Base class that can be used to create custom [Attribute] implementations.
 */
abstract class BaseAttribute : Attribute {
    override val id = UUID.randomUUID()
}