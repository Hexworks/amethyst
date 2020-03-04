package org.hexworks.amethyst.api.mutator

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.BehaviorAccessor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.internal.accessor.DefaultBehaviorMutator

interface BehaviorMutator<C : Context> : BehaviorAccessor<C> {

    /**
     * Adds the given [Behavior] to this [Entity].
     */
    fun addBehavior(behavior: Behavior<C>)

    /**
     * Removes the given [Behavior] from this [Entity].
     */
    fun removeBehavior(behavior: Behavior<C>)

    companion object {

        fun <C : Context> create(behaviors: Set<Behavior<C>>): BehaviorMutator<C> = DefaultBehaviorMutator(behaviors)
    }
}
