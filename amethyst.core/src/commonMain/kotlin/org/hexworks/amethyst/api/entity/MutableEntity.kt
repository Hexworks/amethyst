package org.hexworks.amethyst.api.entity

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Context
import org.hexworks.amethyst.api.mutator.AttributeMutator
import org.hexworks.amethyst.api.mutator.BehaviorMutator
import org.hexworks.amethyst.api.mutator.FacetMutator
import org.hexworks.amethyst.api.system.System

/**
 * An [MutableEntity] is an object composed of [Attribute]s and [System]s representing a cohesive whole.
 * The difference form [Entity] is that a [MutableEntity] enables adding and removing systems and attributes.
 */
interface MutableEntity<out T : EntityType, C : Context> : Entity<T, C>, AttributeMutator, FacetMutator<C>, BehaviorMutator<C>