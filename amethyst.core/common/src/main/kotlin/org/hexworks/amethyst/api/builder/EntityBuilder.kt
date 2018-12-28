package org.hexworks.amethyst.api.builder

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.internal.DefaultEntity

class EntityBuilder<T : EntityType, U : Context>(private val type: T) {

    private var attributes = setOf<Attribute>()
    private var systems = setOf<System<U>>()

    fun attributes(vararg attributes: Attribute) = also {
        this.attributes = attributes.toSet()
    }

    fun systems(vararg systems: System<U>) = also {
        this.systems = systems.toSet()
    }

    fun build(): Entity<T, U> = DefaultEntity(
            type = type,
            attributes = attributes.toSet(),
            systems = systems.toSet())

}
