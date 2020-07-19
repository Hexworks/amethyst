package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.core.api.UUID

/**
 * Base class which can be used for [EntityType]s.
 */
abstract class BaseEntityType(override val name: String = "unknown",
                              override val description: String = "",
                              override val id: UUID = UUID.randomUUID()) : EntityType
