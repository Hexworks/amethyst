package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

/**
 * A [System] is responsible for updating the internal state
 * ([Attribute]s) of an [Entity].
 */
interface System<C : Context> {

    /**
     * The unique identifier of this [System]
     */
    val id: UUID

    /**
     * The [Set] of [Attribute]s which must be present in this [System]
     */
    val mandatoryAttributes: Set<KClass<out Attribute>>
}
