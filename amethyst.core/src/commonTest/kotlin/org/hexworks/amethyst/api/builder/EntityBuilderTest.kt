package org.hexworks.amethyst.api.builder

import org.hexworks.amethyst.api.*
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EntityBuilderTest {

    @Test
    fun given_an_entity_with_mandatory_attribute_for_facet_when_building_it_without_one_it_should_fail() {

        assertFailsWith<IllegalArgumentException>(
                message = "Can't create Entity because there are missing attributes: MandatoryAttribute.") {
            newEntityOfType<TestType, TestContext>(TestType) {
                facets(FacetWithMandatoryAttribute)
            }
        }
    }

    @Test
    fun given_an_entity_with_mandatory_attribute_for_behavior_when_building_it_without_one_it_should_fail() {

        assertFailsWith<IllegalArgumentException>(
                message = "Can't create Entity because there are missing attributes: MandatoryAttribute.") {
            newEntityOfType<TestType, TestContext>(TestType) {
                behaviors(BehaviorWithMandatoryAttribute)
            }
        }
    }

    @Test
    fun given_an_entity_with_mandatory_attribute_for_facet_when_building_it_wit_one_it_should_succeed() {

        val result = newEntityOfType<TestType, TestContext>(TestType) {
            attributes(MandatoryAttribute)
            facets(FacetWithMandatoryAttribute)
        }

        assertTrue {
            result.findFacet(FacetWithMandatoryAttribute::class).isPresent and
                    result.findAttribute(MandatoryAttribute::class).isPresent
        }
    }

    @Test
    fun given_an_entity_with_mandatory_attribute_for_behavior_when_building_it_wit_one_it_should_succeed() {

        val result = newEntityOfType<TestType, TestContext>(TestType) {
            attributes(MandatoryAttribute)
            behaviors(BehaviorWithMandatoryAttribute)
        }

        assertTrue {
            result.findBehavior(BehaviorWithMandatoryAttribute::class).isPresent and
                    result.findAttribute(MandatoryAttribute::class).isPresent
        }
    }

    object MandatoryAttribute : Attribute

    object BehaviorWithMandatoryAttribute : BaseBehavior<TestContext>(MandatoryAttribute::class) {
        override suspend fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
            TODO("not implemented")
        }
    }

    object FacetWithMandatoryAttribute : BaseFacet<TestContext>(MandatoryAttribute::class) {
        override suspend fun executeCommand(command: Command<out EntityType, TestContext>): Response {
            TODO("not implemented")
        }
    }

    object TestType : BaseEntityType("test")

    object TestContext : Context
}
