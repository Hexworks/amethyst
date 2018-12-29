package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.*
import org.hexworks.cobalt.datatypes.Identifier
import kotlin.reflect.KClass

/**
 * A [System] is responsible for updating the internal state
 * ([Attribute]s) of an [Entity].
 */
interface System<C : Context> {

    val id: Identifier
    val mandatoryAttributes: Set<KClass<out Attribute>>
}
