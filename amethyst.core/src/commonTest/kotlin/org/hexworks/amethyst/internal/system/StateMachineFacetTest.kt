package org.hexworks.amethyst.internal.system

import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.message.StateChanged
import org.hexworks.amethyst.platform.runTest
import org.hexworks.amethyst.samples.*
import org.hexworks.amethyst.samples.StateMachineSample.*
import org.hexworks.amethyst.samples.StateMachineSample.BarrierAction.Open
import org.hexworks.amethyst.samples.StateMachineSample.BarrierAction.Unlock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
class StateMachineFacetTest {

    lateinit var target: StateMachineFacet<MyContext, BarrierAction>
    lateinit var entity: Entity<MyType, MyContext>

    @BeforeTest
    fun setUp() {
        target = StateMachineFacet(BarrierAction::class, Unlockable)
        entity = EntityBuilder.newBuilder<MyType, MyContext>(MyType)
                .facets(target)
                .build()
    }

    @Test
    fun When_receiving_invalid_command_Then_state_shouldnt_change() = runTest {
        target.receive(Open(MyContext, entity))

        assertEquals(
                expected = Unlockable,
                actual = target.currentState
        )
    }

    @Test
    fun When_trying_bad_command_Then_state_shouldnt_change() = runTest {
        target.tryReceive(BadMessage(MyContext, entity))

        assertEquals(
                expected = Unlockable,
                actual = target.currentState
        )
    }

    @Test
    fun When_receiving_invalid_command_Then_response_should_be_pass() = runTest {
        val response = target.receive(Open(MyContext, entity))

        assertEquals(
                expected = Pass,
                actual = response
        )
    }

    @Test
    fun When_receiving_valid_command_Then_response_should_be_state_changed() = runTest {
        val response = target.receive(Unlock(MyContext, entity))

        assertEquals(
                expected = MessageResponse(StateChanged(
                        context = MyContext,
                        source = entity,
                        oldState = Unlockable,
                        newState = Openable
                )),
                actual = response
        )
    }

    @Test
    fun When_trying_bad_command_Then_response_should_be_pass() = runTest {
        val response = target.tryReceive(BadMessage(MyContext, entity))

        assertEquals(
                expected = Pass,
                actual = response
        )
    }

    data class BadMessage(
            override val context: MyContext,
            override val source: Entity<out EntityType, MyContext>
    ) : Message<MyContext>
}
