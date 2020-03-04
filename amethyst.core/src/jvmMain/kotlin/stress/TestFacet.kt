package stress

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object TestFacet : BaseFacet<TestContext>() {

    override suspend fun executeCommand(command: Command<out EntityType, TestContext>) = Pass
}
