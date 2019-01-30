package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.internal.accessor.DefaultBehaviorAccessor
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface BehaviorAccessor<C : Context> {

    /**
     * A [Sequence] of the current [Behavior]s of this [Entity].
     */
    val behaviors: Sequence<Behavior<C>>

    /**
     * Tells whether this [BehaviorAccessor] has [Behavior]s or not.
     */
    val hasBehaviors: Boolean

    /**
     * Returns the [Behavior] of the given class [T] (if any).
     */
    fun <T : Behavior<C>> findBehavior(klass: KClass<T>): Maybe<T>

    /**
     * Adds the given [Behavior] to this [Entity].
     */
    fun addBehavior(behavior: Behavior<C>)

    /**
     * Removes the given [Behavior] from this [Entity].
     */
    fun removeBehavior(behavior: Behavior<C>)

    companion object {

        fun <C : Context> create(behaviors: Set<Behavior<C>>): BehaviorAccessor<C> = DefaultBehaviorAccessor(behaviors)
    }
}
