package org.hexworks.amethyst.api

import org.hexworks.cobalt.core.api.UUID

/**
 * An [Attribute] represents metadata about an entity that can change over time.
 */
interface Attribute {
    val id: UUID
}
