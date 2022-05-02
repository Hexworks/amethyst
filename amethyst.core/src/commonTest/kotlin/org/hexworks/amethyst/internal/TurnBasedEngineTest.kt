package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.amethyst.platform.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TurnBasedEngineTest {

    lateinit var target: TurnBasedEngine<TestContext>

    @BeforeTest
    fun setUp() {
        target = Engine.create()
    }

    @Test
    fun given_multiple_entities_all_should_be_updated_when_the_engine_is_updated() = runTest {

        val updates: MutableMap<Int, Boolean> = mutableMapOf(
            0 to false,
            1 to false
        )

        target.addEntity(newEntityOfType(Type0) {
            behaviors(object : BaseBehavior<TestContext>() {
                override suspend fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
                    updates[0] = true
                    return true
                }
            })
        })
        target.addEntity(newEntityOfType(Type1) {
            behaviors(object : BaseBehavior<TestContext>() {
                override suspend fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
                    updates[1] = true
                    return true
                }
            })
        })

        target.executeTurn(TestContext).join()

        assertTrue("Behaviors were not updated") {
            updates[0]!! && updates[1]!!
        }
    }

    object TestContext : Context

    object Type0 : BaseEntityType() {
        override val name = "Type 0"
        override val description = "Type 0"
    }

    object Type1 : BaseEntityType() {
        override val name = "Type 1"
        override val description = "Type 1"
    }

}
