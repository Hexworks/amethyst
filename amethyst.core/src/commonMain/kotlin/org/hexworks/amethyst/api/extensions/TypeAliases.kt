package org.hexworks.amethyst.api.extensions

import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.system.Facet

typealias FacetWithContext<C> = Facet<out C, out Message<out C>>
