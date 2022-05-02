package org.hexworks.amethyst.internal.accessor

import kotlinx.collections.immutable.toPersistentSet
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.mutator.BehaviorMutator
import org.hexworks.amethyst.api.system.Behavior
import kotlin.reflect.KClass

class DefaultBehaviorMutator<C : Context>(behaviors: Set<Behavior<C>>) : BehaviorMutator<C> {

    override val behaviors: Sequence<Behavior<C>>
        get() = currentBehaviors.asSequence()

    override val hasBehaviors: Boolean
        get() = currentBehaviors.isNotEmpty()

    private var currentBehaviors = behaviors.toPersistentSet()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Behavior<C>> findBehaviorOrNull(klass: KClass<T>): T? {
        return currentBehaviors.firstOrNull { klass.isInstance(it) } as? T
    }

    override fun addBehavior(behavior: Behavior<C>) {
        currentBehaviors = currentBehaviors.add(behavior)
    }

    override fun removeBehavior(behavior: Behavior<C>) {
        currentBehaviors = currentBehaviors.remove(behavior)
    }
}
