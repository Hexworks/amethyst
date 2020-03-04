package stress

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.core.api.UUID

data class TestAttribute(
        val name: String,
        val age: Int,
        val id: UUID
) : Attribute
