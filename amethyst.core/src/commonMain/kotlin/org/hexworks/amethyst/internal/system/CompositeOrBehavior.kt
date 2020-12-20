package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.cobalt.logging.api.LoggerFactory

class CompositeOrBehavior<C : Context>(private val first: Behavior<C>, private val second: Behavior<C>) : BaseBehavior<C>() {

    private val logger = LoggerFactory.getLogger(this::class)

    override suspend fun update(entity: Entity<EntityType, C>, context: C): Boolean {
        val firstResult = first.update(entity, context)
        if (firstResult) {
            logger.debug {
                "Performing update with composite or behavior ($first and $second) on entity: $entity short circuited " +
                        "because update for first returned `true`. Skipping second."
            }
        }
        return firstResult || second.update(entity, context)
    }

}
