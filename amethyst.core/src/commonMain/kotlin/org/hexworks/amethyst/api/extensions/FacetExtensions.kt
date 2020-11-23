package org.hexworks.amethyst.api.extensions

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.amethyst.internal.system.StateMachineFacet
import kotlin.reflect.KClass

/**
 * Creates a state machine out of this [Facet] using [commonAncestor] as
 * the acceptable message type.
 *
 * @sample org.hexworks.amethyst.samples.StateMachineSample
 */
fun <C : Context, A : Message<C>, M : A> Facet<C, M>.toStateMachine(
        commonAncestor: KClass<A>
): Facet<C, A> = StateMachineFacet(
        messageType = commonAncestor,
        initialState = this
)
