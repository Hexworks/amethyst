package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseSystem

class CompositeOrSystem<C : Context>(private val subsystems: List<System<C>>) : BaseSystem<C>() {

    override fun update(entity: Entity<EntityType, C>, context: C): Boolean {
        for (system in subsystems) {
            if (system.update(entity, context)) {
                return true
            }
        }
        return false
    }

    override fun executeCommand(command: Command<out EntityType, C>): Boolean {
        for (system in subsystems) {
            if (system.executeCommand(command)) {
                return true
            }
        }
        return false
    }
}
