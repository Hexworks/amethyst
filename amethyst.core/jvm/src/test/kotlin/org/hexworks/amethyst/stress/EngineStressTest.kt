package org.hexworks.amethyst.stress

import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.Engines
import org.hexworks.amethyst.api.Entities
import org.hexworks.cobalt.datatypes.Identifier
import kotlin.system.measureNanoTime
import kotlin.test.BeforeTest
import kotlin.test.Test

class EngineStressTest {

    lateinit var target: Engine<TestContext>

    @BeforeTest
    fun set_up() {
        target = Engines.newEngine()
    }

    @Test
    fun given_a_lot_of_entities_update_should_run_fast() {
        repeat(100) { idx ->
            target.addEntity(Entities.newEntityOfType(TestEntityType) {
                attributes(TestAttribute(
                        name = "name $idx",
                        age = idx,
                        id = Identifier.randomIdentifier()))
                facets(TestFacet)
                behaviors(TestBehavior)
            })
        }
        var counter = 0
        var timeSumMs = 0L
        val measurements = mutableListOf<Long>()
        repeat(1_000) { idx ->
            val nano = measureNanoTime {
                target.update(TestContext)
            }
            counter++
            timeSumMs+= nano / 1000 / 1000
            if(counter == 100) {
                counter = 0
                val measurement = timeSumMs / 100
                measurements.add(measurement)
                timeSumMs = 0
                println("Average update with 100 iterations: ${measurement}ms")
            }
        }
    }
}
