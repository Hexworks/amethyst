package stress

import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity

class TestMessage(
    override val context: TestContext,
    override val source: Entity<TestEntityType, TestContext>
) : Message<TestContext>
