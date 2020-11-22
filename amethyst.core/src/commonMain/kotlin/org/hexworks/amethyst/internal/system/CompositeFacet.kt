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
class CompositeFacet<C : Context, P : Command<C>>(
        private val children: Set<Facet<C, P>>,
        override val commandType: KClass<P>,
        override val id: UUID = UUID.randomUUID(),
        override val mandatoryAttributes: Set<KClass<out Attribute>> = children.flatMap {
            it.mandatoryAttributes
        }.toSet()
) : Facet<C, P> {

    override suspend fun executeCommand(command: P) = tryExecuteCommand(command)

    override suspend fun tryExecuteCommand(command: Command<C>): Response {
        val iter = children.iterator()
        var response: Response = Pass
        while (iter.hasNext() && response != Consumed && response !is CommandResponse<*>) {
            response = iter.next().tryExecuteCommand(command)
        }
        return response
    }

    override suspend fun compose(other: Facet<C, P>, commonAncestor: KClass<P>): Facet<C, P> {
        return super.compose(other, commonAncestor)
    }
}
