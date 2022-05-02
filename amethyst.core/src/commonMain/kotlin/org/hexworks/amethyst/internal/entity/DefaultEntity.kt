package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.extensions.FacetWithContext
import org.hexworks.amethyst.api.system.Behavior
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.logging.api.LoggerFactory

class DefaultEntity<T : EntityType, C : Context>(
    type: T,
    attributes: Set<Attribute> = setOf(),
    facets: Set<FacetWithContext<C>> = setOf(),
    behaviors: Set<Behavior<C>> = setOf()
) : BaseEntity<T, C>(
    type = type,
    attributes = attributes.plus(type),
    facets = facets,
    behaviors = behaviors
) {

    private val eventStack = mutableListOf<Message<C>>()
    private val logger = LoggerFactory.getLogger(Entity::class)

    override val needsUpdate: Boolean
        get() = hasBehaviors || eventStack.isNotEmpty()

    override suspend fun sendMessage(message: Message<C>): Boolean {
        logger.debug("Sending message '$message' to entity '$this'.")
        eventStack.add(message)
        return false
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun receiveMessage(message: Message<C>): Response {
        logger.debug("Receiving message '$message' in entity $this.")
        return if (hasFacets) {
            val iter: Iterator<Facet<C, Message<C>>> = facets.iterator() as Iterator<Facet<C, Message<C>>>
            var response: Response = Pass
            var lastCommand = message
            while (iter.hasNext() && response != Consumed) {
                response = iter.next().tryReceive(lastCommand)
                // TODO: we need to process responses while they are CommandResponses!
                if (response is MessageResponse<*>) {
                    lastCommand = response.message as Message<C>
                }
            }
            response
        } else {
            Pass
        }
    }

    override suspend fun update(context: C): Boolean {
        val events = eventStack.toList()
        eventStack.clear()
        events.forEach {
            receiveMessage(it)
        }
        return behaviors.fold(false) { result, behavior ->
            result or behavior.update(this, context)
        }
    }

    override fun toString() = type.name
}
