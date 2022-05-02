package org.hexworks.amethyst.api.system

import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.entity.EntityType

/**
 * A [Actor] is a [System] that's a combination of a [Behavior] and a [Facet].
 * This means that it performs autonomous actions on [update] and can respond
 * to [Message]s.
 *
 * A good example of an [Actor] is a [System] that handles the `energy` of an
 * entity. On each update it is drained a little bit, but when actions are
 * performed that require `energy` a bigger amount will be subtracted.
 */
interface Actor<T : EntityType, C : Context, P : Message<C>> : Facet<C, P>, Behavior<C>
