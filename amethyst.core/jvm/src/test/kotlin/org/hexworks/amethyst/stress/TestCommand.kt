package org.hexworks.amethyst.stress

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.entity.Entity

class TestCommand(override val context: TestContext,
                  override val source: Entity<TestEntityType, TestContext>) : Command<TestEntityType, TestContext>
