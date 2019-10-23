package org.hexworks.amethyst.stress

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.datatypes.Identifier

data class TestAttribute(val name: String,
                         val age: Int,
                         val id: Identifier) : Attribute
