package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute

/**
 * An [EntityType] is a mandatory [Attribute] of every [Entity]
 * and it describes what the entity is doing.
 *
 * An [EntityType] must be comparable to an other [EntityType]
 */
interface EntityType : Attribute {

    /**
     * The name of the entity.
     */
    val name: String

    /**
     * A description for the entity.
     */
    val description: String
}
