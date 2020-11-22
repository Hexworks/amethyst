package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior

class CompositeAndBehavior<C : Context>(
        private val first: Behavior<C>,
        private val second: Behavior<C>
) : BaseBehavior<C>() {

    override suspend fun update(entity: Entity<out EntityType, C>, context: C): Boolean {
        return first.update(entity, context) && second.update(entity, context)
    }

}
