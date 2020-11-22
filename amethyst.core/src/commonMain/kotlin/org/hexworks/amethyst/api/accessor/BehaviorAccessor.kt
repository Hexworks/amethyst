package org.hexworks.amethyst.api.accessor

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.mutator.BehaviorMutator
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.internal.accessor.DefaultBehaviorMutator
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

interface BehaviorAccessor<C : Context> {

    /**
     * A [Sequence] of the current [Behavior]s of this [Entity].
     */
    val behaviors: Sequence<Behavior<C>>

    /**
     * Tells whether this [BehaviorMutator] has [Behavior]s or not.
     */
    val hasBehaviors: Boolean

    /**
     * Returns the [Behavior] of the given class [T] (if any).
     */
    fun <T : Behavior<C>> findBehavior(klass: KClass<T>) = Maybe.ofNullable(findBehaviorOrNull(klass))

    /**
     * Returns the [Behavior] of the given class [T] (if any).
     */
    fun <T : Behavior<C>> findBehaviorOrNull(klass: KClass<T>): T?


    companion object {

        @JvmStatic
        fun <C : Context> create(behaviors: Set<Behavior<C>>): BehaviorAccessor<C> = DefaultBehaviorMutator(behaviors)
    }
}
