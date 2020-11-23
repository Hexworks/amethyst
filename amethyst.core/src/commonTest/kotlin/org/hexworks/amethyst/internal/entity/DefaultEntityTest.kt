package org.hexworks.amethyst.internal.entity

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.core.platform.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultEntityTest {

    lateinit var target: Entity<TestType, TestContext>

    @BeforeTest
    fun setUp() {
        target = newEntityOfType(TestType) {
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
        target.asMutableEntity().addAttribute(AddedAttribute)

        assertEquals(
                expected = setOf(InitialAttribute, TestType, AddedAttribute),
                actual = target.attributes.toSet(),
                message = "Target doesn't have the expected attributes")
        assertEquals(expected = 3,
                actual = target.attributes.toList().size)
    }

    @Test
    fun given_an_entity_it_should_have_the_added_behavior_after_it_is_added() {
        target.asMutableEntity().addBehavior(AddedBehavior)

        assertEquals(
                expected = setOf(InitialBehavior, AddedBehavior),
                actual = target.behaviors.toSet(),
                message = "Target doesn't have the expected added behavior")
    }

    @Test
    fun given_an_entity_it_should_have_the_added_facet_after_it_is_added() {
        target.asMutableEntity().addFacet(AddedFacet)

        assertEquals(
                expected = setOf(InitialFacet, AddedFacet),
                actual = target.facets.toSet(),
                message = "Target doesn't have the expected added facet")
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_attribute_after_it_is_removed() {
        target.asMutableEntity().addAttribute(AddedAttribute)
        target.asMutableEntity().removeAttribute(AddedAttribute)

        assertEquals(
                expected = setOf(InitialAttribute, TestType),
                actual = target.attributes.toSet(),
                message = "Target still has the removed attribute")
        assertEquals(expected = 2,
                actual = target.attributes.toList().size)
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_behavior_after_it_is_removed() {
        target.asMutableEntity().addBehavior(AddedBehavior)
        target.asMutableEntity().removeBehavior(AddedBehavior)

        assertEquals(
                expected = setOf(InitialBehavior),
                actual = target.behaviors.toSet(),
                message = "Target still has the added behavior")
    }

    @Test
    fun given_an_entity_it_should_not_have_the_added_facet_after_it_is_removed() {
        target.asMutableEntity().addFacet(AddedFacet)
        target.asMutableEntity().removeFacet(AddedFacet)

        assertEquals(
                expected = setOf(InitialFacet),
                actual = target.facets.toSet(),
                message = "Target still has the added facet")
    }

    @Test
    fun given_an_entity_with_a_behavior_which_updates_it_should_update_when_a_command_is_sent() = runTest {
        val updatingBehavior = UpdatingBehavior()

        target.asMutableEntity().addBehavior(updatingBehavior)

        target.update(TestContext)

        assertTrue("Behavior was not updated") {
            updatingBehavior.wasUpdatedWith(target, TestContext)
        }
    }

    @Test
    fun given_an_entity_with_a_behavior_which_updates_it_should_report_being_updated_when_a_command_is_sent() = runTest {
        val updatingBehavior = UpdatingBehavior()

        target.asMutableEntity().addBehavior(updatingBehavior)

        val result = target.update(TestContext)

        assertTrue("Entity was not updated") {
            result
        }
    }

    @Test
    fun given_an_entity_with_a_facet_which_consumes_commands_it_should_consume_the_command_when_a_command_is_sent() = runTest {
        val consumingFacet = ConsumingFacet()

        target.asMutableEntity().addFacet(consumingFacet)

        val command = TestMessage(target)

        target.executeCommand(command)

        assertTrue("Facet did not receive command") {
            consumingFacet.wasGivenCommand(command)
        }
    }

    @Test
    fun given_an_entity_with_a_facet_which_consumes_commands_it_shouldnt_when_another_facet_already_consumed_the_command() = runTest {
        val consumingFacet = ConsumingFacet()
        val wannabeConsumingFacet = WannabeConsumingFacet()

        target.asMutableEntity().addFacet(consumingFacet)
        target.asMutableEntity().addFacet(wannabeConsumingFacet)

        val command = TestMessage(target)

        target.executeCommand(command)

        assertTrue("Facet received the command") {
            wannabeConsumingFacet.wasGivenCommand(command).not()
        }
    }

    @Test
    fun given_an_entity_with_two_identical_attributes_added_it_should_have_only_one_more() {
        val initialAttributesNumber = target.attributes.toList().size

        val dataAttribute = DataAttribute(1)

        target.asMutableEntity().addAttribute(dataAttribute)
        target.asMutableEntity().addAttribute(dataAttribute)

        assertEquals(initialAttributesNumber + 1, target.attributes.toList().size);
    }

    class TestMessage(
            override val source: Entity<TestType, TestContext>,
            override val context: TestContext = TestContext
    ) : Message<TestContext>

    object InitialAttribute : BaseAttribute()
    object AddedAttribute : BaseAttribute()

    data class DataAttribute(val data: Int) : BaseAttribute()

    object InitialBehavior : TestBehavior(false)
    object AddedBehavior : TestBehavior(false)
    class UpdatingBehavior : TestBehavior(true)

    object InitialFacet : TestFacet(Pass)
    object AddedFacet : TestFacet(Pass)
    class ConsumingFacet : TestFacet(Consumed)
    class WannabeConsumingFacet : TestFacet(Consumed)

    abstract class TestBehavior(private val updateResult: Boolean) : BaseBehavior<TestContext>() {

        private val updatedWith = mutableListOf<Pair<Entity<EntityType, TestContext>, TestContext>>()

        fun wasUpdatedWith(entity: Entity<out EntityType, TestContext>, context: TestContext): Boolean {
            return updatedWith.contains(entity to context)
        }

        @Suppress("UNCHECKED_CAST")
        override suspend fun update(entity: Entity<out EntityType, TestContext>, context: TestContext): Boolean {
            updatedWith.add(entity as Entity<EntityType, TestContext> to context)
            return updateResult
        }
    }

    abstract class TestFacet(
            private val response: Response
    ) : BaseFacet<TestContext, TestMessage>(TestMessage::class) {

        private val givenCommands = mutableListOf<Message<TestContext>>()

        fun wasGivenCommand(message: Message<TestContext>): Boolean {
            return givenCommands.contains(message)
        }

        override suspend fun receive(message: TestMessage): Response {
            givenCommands.add(message)
            return response
        }
    }

    object TestContext : Context

    object TestType : BaseEntityType(
            name = "test",
            description = "this is a test entity")
}
