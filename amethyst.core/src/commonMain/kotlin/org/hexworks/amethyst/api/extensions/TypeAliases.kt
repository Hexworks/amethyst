package org.hexworks.amethyst.api.extensions

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.system.Facet

typealias FacetWithContext<C> = Facet<out C, out Command<out C>>
