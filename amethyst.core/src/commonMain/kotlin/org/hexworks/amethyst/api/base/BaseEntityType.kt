package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.core.api.UUID

/**
 * Base class which can be used for [EntityType]s.
 */
abstract class BaseEntityType(
        override val name: String = "unknown",
        override val description: String = "",
        override val id: UUID = UUID.randomUUID()
) : EntityType {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BaseEntityType

        if (id != other.id) return false

        return true
    }

    final override fun hashCode(): Int {
        return id.hashCode()
    }
}
