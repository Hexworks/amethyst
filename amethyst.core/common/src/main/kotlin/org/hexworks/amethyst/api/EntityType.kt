package org.hexworks.amethyst.api

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
