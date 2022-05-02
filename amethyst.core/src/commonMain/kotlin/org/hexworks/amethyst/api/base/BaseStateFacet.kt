@file:Suppress("UNCHECKED_CAST")

package org.hexworks.amethyst.api.base

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.system.StateFacet
import kotlin.reflect.KClass

abstract class BaseStateFacet<C : Context, P : Message<C>>(
    messageType: KClass<P>,
    vararg mandatoryAttributes: KClass<out Attribute>
) : BaseFacet<C, P>(messageType, *mandatoryAttributes), StateFacet<C, P>
