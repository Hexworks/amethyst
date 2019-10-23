package org.hexworks.amethyst.internal.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.accessor.BehaviorAccessor
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class DefaultBehaviorAccessor<C : Context>(behaviors: Set<Behavior<C>>) : BehaviorAccessor<C> {

    override val behaviors: Sequence<Behavior<C>>
        get() = currentBehaviors.toSet().asSequence()

    override val hasBehaviors: Boolean
        get() = currentBehaviors.isNotEmpty()

    private val currentBehaviors = behaviors.toMutableSet()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Behavior<C>> findBehavior(klass: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(currentBehaviors.firstOrNull { klass.isInstance(it) } as? T)
    }

    override fun addBehavior(behavior: Behavior<C>) {
        currentBehaviors.add(behavior)
    }

    override fun removeBehavior(behavior: Behavior<C>) {
        currentBehaviors.remove(behavior)
    }
}
