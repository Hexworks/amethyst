package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.system.Facet

class CompositeAndFacet<C : Context>(private val first: Facet<C>, private val second: Facet<C>) : BaseFacet<C>() {

    override fun executeCommand(command: Command<out EntityType, C>): Boolean {
        return first.executeCommand(command) && second.executeCommand(command)
    }

}
