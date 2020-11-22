package stress

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.entity.Entity

class TestCommand(
        override val context: TestContext,
        override val source: Entity<TestEntityType, TestContext>
) : Command<TestContext>
