package stress

import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object TestFacet : BaseFacet<TestContext, TestCommand>(TestCommand::class) {

    override suspend fun executeCommand(command: TestCommand): Response {
        return Pass
    }
}
