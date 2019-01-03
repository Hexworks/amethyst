package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute

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