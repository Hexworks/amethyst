package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.CommandResponse
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

@Suppress("DuplicatedCode")
class CompositeFacet<T : EntityType, C : Context, P : Command<T, C>>(
        private val children: Set<Facet<T, C, P>>,
        override val id: UUID = UUID.randomUUID(),
        override val mandatoryAttributes: Set<KClass<out Attribute>> = children.flatMap {
            it.mandatoryAttributes
        }.toSet()
) : Facet<T, C, P> {

    override suspend fun executeCommand(command: P): Response {
        val iter = children.iterator()
        var response: Response = Pass
        while (iter.hasNext() && response != Consumed && response !is CommandResponse<*>) {
            response = iter.next().executeCommand(command)
        }
        return response
    }

    override suspend fun plus(other: Facet<T, C, P>): Facet<T, C, P> {
        return CompositeFacet(
                children = children + other
        )
    }
}