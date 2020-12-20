package org.hexworks.amethyst.api.message

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet

data class StateChanged<C : Context>(
        override val context: C,
        override val source: Entity<EntityType, C>,
        val oldState: Facet<C, out Message<C>>,
        val newState: Facet<C, out Message<C>>
) : Message<C>
