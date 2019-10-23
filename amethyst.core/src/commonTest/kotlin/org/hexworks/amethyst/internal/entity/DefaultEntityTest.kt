package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultEntityTest {

    lateinit var target: Entity<TestType, TestContext>

    @BeforeTest
    fun setUp() {
        target = Entities.newEntityOfType(TestType) {
            attributes(InitialAttribute)
            behaviors(InitialBehavior)
            facets(InitialFacet)
        }
    }

    @Test
    fun given_an_entity_it_should_have_its_initial_attributes() {
        assertEquals(
                expected = setOf(InitialAttribute, TestType),
                actual = target.attributes.toSet(),
                message = "Target doesn't have the expected initial attribute")
        assertEquals(expected = 2,
                actual = target.attributes.toList().size)
    }

    @Test
    fun given_an_entity_it_should_have_its_initial_behavior() {
        assertEquals(
                expected = setOf(InitialBehavior),
                actual = target.behaviors.toSet(),
                message = "Target doesn't have the expected initial behavior")
    }

    @Test
    fun given_an_entity_it_should_have_its_initial_facet() {
        assertEquals(
                expected = setOf(InitialFacet),
                actual = target.facets.toSet(),
                message = "Target doesn't have the expected initial facet")
    }

    @Test
    fun given_an_entity_it_should_have_the_added_attribute_after_it_is_added() {
        target.addAttribute(AddedAttribute)

        assertEquals(
                expected = setOf(InitialAttribute, TestType, AddedAttribute),
                actual = target.attributes.toSet(),
                message = "Target doesn't have the expected attributes")
        assertEquals(expected = 3,
                actual = target.attributes.toList().size)
    }

    @Test
    fun given_an_entity_it_should_have_the_added_behavior_after_it_is_added() {
        target.addBehavior(AddedBehavior)

        assertEquals(
                expected = setOf(InitialBehavior, AddedBehavior),
                actual = target.behaviors.toSet(),
                message = "Target doesn't have the expected added behavior")
    }

    @Test
    fun given_an_entity_it_should_have_the_added_facet_after_it_is_added() {
        target.addFacet(AddedFacet)

        assertEquals(
                expected = setOf(InitialFacet, AddedFacet),
                actual = target.facets.toSet(),
                message = "Target doesn't have the expected added facet")
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_attribute_after_it_is_removed() {
        target.addAttribute(AddedAttribute)
        target.removeAttribute(AddedAttribute)

        assertEquals(
                expected = setOf(InitialAttribute, TestType),
                actual = target.attributes.toSet(),
                message = "Target still has the removed attribute")
        assertEquals(expected = 2,
                actual = target.attributes.toList().size)
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_behavior_after_it_is_removed() {
        target.addBehavior(AddedBehavior)
        target.removeBehavior(AddedBehavior)

        assertEquals(
                expected = setOf(InitialBehavior),
                actual = target.behaviors.toSet(),
                message = "Target still has the added behavior")
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_facet_after_it_is_removed() {
        target.addFacet(AddedFacet)
        target.removeFacet(AddedFacet)

        assertEquals(
                expected = setOf(InitialFacet),
                actual = target.facets.toSet(),
                message = "Target still has the added facet")
    }

    @Test
    fun given_an_entity_with_a_behavior_which_updates_it_should_update_when_a_command_is_sent() {
        val updatingBehavior = UpdatingBehavior()

        target.addBehavior(updatingBehavior)

        target.update(TestContext)

        assertTrue("Behavior was not updated") {
            updatingBehavior.wasUpdatedWith(target, TestContext)
        }
    }

    @Test
    fun given_an_entity_with_a_behavior_which_updates_it_should_report_being_updated_when_a_command_is_sent() {
        val updatingBehavior = UpdatingBehavior()

        target.addBehavior(updatingBehavior)

        val result = target.update(TestContext)

        assertTrue("Entity was not updated") {
            result
        }
    }

    @Test
    fun given_an_entity_with_a_facet_which_consumes_commands_it_should_consume_the_command_when_a_command_is_sent() {
        val consumingFacet = ConsumingFacet()

        target.addFacet(consumingFacet)

        val command = TestCommand(target)

        target.executeCommand(command)

        assertTrue("Facet did not receive command") {
            consumingFacet.wasGivenCommand(command)
        }
    }

    @Test
    fun given_an_entity_with_a_facet_which_consumes_commands_it_shouldnt_when_another_facet_already_consumed_the_command() {
        val consumingFacet = ConsumingFacet()
        val wannabeConsumingFacet = WannabeConsumingFacet()

        target.addFacet(consumingFacet)
        target.addFacet(wannabeConsumingFacet)

        val command = TestCommand(target)

        target.executeCommand(command)

        assertTrue("Facet received the command") {
            wannabeConsumingFacet.wasGivenCommand(command).not()
        }
    }

    class TestCommand(override val source: Entity<TestType, TestContext>,
                      override val context: TestContext = TestContext) : Command<TestType, TestContext>

    object InitialAttribute : Attribute
    object AddedAttribute : Attribute

    object InitialBehavior : TestBehavior(false)
    object AddedBehavior : TestBehavior(false)
    class UpdatingBehavior : TestBehavior(true)

    object InitialFacet : TestFacet(Pass)
    object AddedFacet : TestFacet(Pass)
    class ConsumingFacet : TestFacet(Consumed)
    class WannabeConsumingFacet : TestFacet(Consumed)

    abstract class TestBehavior(private val updateResult: Boolean) : BaseBehavior<TestContext>() {

        private val updatedWith = mutableListOf<Pair<Entity<EntityType, TestContext>, TestContext>>()

        fun wasUpdatedWith(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
            return updatedWith.contains(entity to context)
        }

        override fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
            updatedWith.add(entity to context)
            return updateResult
        }
    }

    abstract class TestFacet(private val response: Response) : BaseFacet<TestContext>() {

        private val givenCommands = mutableListOf<Command<out EntityType, TestContext>>()

        fun wasGivenCommand(command: Command<out EntityType, TestContext>): Boolean {
            return givenCommands.contains(command)
        }

        override fun executeCommand(command: Command<out EntityType, TestContext>): Response {
            givenCommands.add(command)
            return response
        }
    }

    object TestContext : Context

    object TestType : BaseEntityType(
            name = "test",
            description = "this is a test entity")
}
