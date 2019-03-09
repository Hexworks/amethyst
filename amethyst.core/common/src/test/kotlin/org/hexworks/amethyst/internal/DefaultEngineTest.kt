package org.hexworks.amethyst.internal

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.Entities
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class DefaultEngineTest {

    lateinit var target: DefaultEngine<TestContext>

    @BeforeTest
    fun setUp() {
        target = Engine.default()
    }

    @Test
    fun given_multiple_entities_all_should_be_updated_when_the_engine_is_updated() {

        val updates: MutableMap<Int, Boolean> = mutableMapOf(
                0 to false,
                1 to false)

        target.addEntity(Entities.newEntityOfType(Type0) {
            behaviors(object : BaseBehavior<TestContext>() {
                override fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
                    updates[0] = true
                    return true
                }
            })
        })
        target.addEntity(Entities.newEntityOfType(Type1) {
            behaviors(object : BaseBehavior<TestContext>() {
                override fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
                    updates[1] = true
                    return true
                }
            })
        })

        target.update(TestContext)

        assertTrue("Behaviors were not updated") {
            updates[0]!! && updates[1]!!
        }
    }

    object TestContext : Context

    object Type0 : EntityType {
        override val name = "Type 0"
        override val description = "Type 0"
    }

    object Type1 : EntityType {
        override val name = "Type 1"
        override val description = "Type 1"
    }

}