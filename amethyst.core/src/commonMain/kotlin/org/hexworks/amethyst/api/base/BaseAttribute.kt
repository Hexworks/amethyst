package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.core.api.UUID

abstract class BaseAttribute : Attribute {
    override val id = UUID.randomUUID()
}