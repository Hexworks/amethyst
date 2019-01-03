package org.hexworks.amethyst.stress

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.extensions.whenCommandIs

object TestFacet : BaseFacet<TestContext>() {
    override fun executeCommand(command: Command<out EntityType, TestContext>): Response<TestContext> {
        command.whenCommandIs<TestContext, TestCommand> {

        }
        return Pass(command)
    }
}