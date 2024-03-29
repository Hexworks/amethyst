package org.hexworks.amethyst.api.builder

import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EntityBuilderTest {

    @Test
    fun given_an_entity_with_mandatory_attribute_for_facet_when_building_it_without_one_it_should_fail() {

        assertFailsWith<IllegalArgumentException>(
            message = "Can't create Entity because there are missing attributes: MandatoryAttribute."
        ) {
            newEntityOfType<TestType, TestContext>(TestType) {
                facets(FacetWithMandatoryAttribute)
            }
        }
    }

    @Test
    fun given_an_entity_with_mandatory_attribute_for_behavior_when_building_it_without_one_it_should_fail() {

        assertFailsWith<IllegalArgumentException>(
            message = "Can't create Entity because there are missing attributes: MandatoryAttribute."
        ) {
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
            result.findFacetOrNull(FacetWithMandatoryAttribute::class) != null &&
                    result.findAttributeOrNull(MandatoryAttribute::class) != null
        }
    }

    @Test
    fun given_an_entity_with_mandatory_attribute_for_behavior_when_building_it_wit_one_it_should_succeed() {

        val result = newEntityOfType<TestType, TestContext>(TestType) {
            attributes(MandatoryAttribute)
            behaviors(BehaviorWithMandatoryAttribute)
        }

        assertTrue {
            result.findBehaviorOrNull(BehaviorWithMandatoryAttribute::class) != null &&
                    result.findAttributeOrNull(MandatoryAttribute::class) != null
        }
    }

    object MandatoryAttribute : BaseAttribute()

    object BehaviorWithMandatoryAttribute : BaseBehavior<TestContext>(MandatoryAttribute::class) {
        override suspend fun update(entity: Entity<EntityType, TestContext>, context: TestContext): Boolean {
            TODO("not implemented")
        }
    }

    object FacetWithMandatoryAttribute :
        BaseFacet<TestContext, TestMessage>(TestMessage::class, MandatoryAttribute::class) {
        override suspend fun receive(message: TestMessage): Response {
            TODO("Not yet implemented")
        }
    }

    object TestType : BaseEntityType("test")

    object TestContext : Context

    object TestMessage : Message<TestContext> {
        override val context: TestContext
            get() = TODO("Not yet implemented")
        override val source: Entity<TestType, TestContext>
            get() = TODO("Not yet implemented")
    }
}
