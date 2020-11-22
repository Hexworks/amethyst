package stress

import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object TestBehavior : BaseBehavior<TestContext>() {

    override suspend fun update(entity: Entity<out EntityType, TestContext>, context: TestContext) = false
}
