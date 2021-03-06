
![Logo](https://github.com/Hexworks/amethyst/blob/master/amethyst_logo_PREVIEW_shine_darkbg.png)

# Introducing Amethyst

*Amethyst* is a small library that enables you to modularize your application by letting you decompose them into
**Systems**, **Entities** and **Attributes** in an easy-to-use manner. It is similar to an[Entity Component System](https://en.wikipedia.org/wiki/Entity_component_system)
but it is a bit different in some important aspects.

The core abstraction in *Amethyst* is the **Entity** that encapsulates the internal state of our business objects and
also their related operations. By using this concept we can maintain the [Cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))
of our business objects while still retaining the flexibility.

`So how does this work?`, you might ask. Let's take a look at a simple example.

## Systems, Entities, Attributes

Let's say that we're creating a game with mythical creatures, and we want to add a *goblin* to our game.
If we think about how things can behave and change over time we can conclude, that there are essentially two ways:
*internal*, and *external*. For example a *goblin* can decide  that it wants to explore a dungeon, or just take a
look around (**internal change**), or the player can decide to bash the goblin to a pulpy mass with their club
(**external change**). For this Amethyst has `System`s in place:

```kotlin
/**
 * A [System] is responsible for updating the internal state of an [Entity].
 * The internal state is represented by [Attribute]s.
 */
interface System<C : Context>
```

There are several types of `System`s, and for our discussion here we need to know about `Behavior`:

```kotlin
/**
 * A [Behavior] is a [System] that performs autonomous actions whenever
 * [update] is called on them.
 */
interface Behavior<C : Context> : System<C>
```

that lets our *goblin* interact with the world and `Facet`:

```kotlin
/**
 * A [Facet] is a [System] that performs actions based on the [Message] they receive.
 * Each [Facet] only accepts a single type of [Message] of type [M]. This enforces
 * that facets adhere to the *Single Responsibility Principle*.
 */
interface Facet<C : Context, M : Message<C>> : System<C>
```

that lets whe **world** interact with our *goblin*.

> There is also `Actor` which combines `Facet` and `Behavior`. We'll talk about it later.

When changes happen over time to an entity (our *goblin* in this example) its state might change.
To represent this, *Amethyst* gives us `Attribute`s:

```kotlin
/**
 * An [Attribute] represents metadata about an entity that can change over time.
 */
interface Attribute
```

An `Attribute` can be anything that you add to your *entity*, from health points to stomach contents.
What's important is that `Attribute`s should **never have** behavior, they are supposed to be dumb data structures.

On the other hand, `System`s should never have internal state. These two *important rules* allow us to compose `System`s
and `Attribute`s into `Entity` objects that are flexible, cohesive and safe to use.

The `Entity` itself is just a bag of `Attribute`s and `System`s:

```kotlin
interface Entity<T : EntityType, C : Context> : AttributeAccessor, FacetAccessor<C>, BehaviorAccessor<C> {
    
    val id: UUID
    val type: T
    val name: String
    val description: String

    val attributes: Sequence<Attribute>
    val behaviors: Sequence<Behavior<C>>
    val facets: Sequence<FacetWithContext<C>>

    suspend fun sendMessage(message: Message<C>): Boolean

    suspend fun receiveMessage(message: Message<C>): Response

    suspend fun update(context: C): Boolean
}
```

What's interesting here is `sendMessage`, `receiveMessage` and `update`. It is not a coincidence that we have these in
`Facet` and `Behavior`! When an `Entity` receives a `Message` it will try to apply it to its `Facet`s, and when an `Entity`
is updated it lets its `Behavior`s interact with the world. What *world* means in this context is up to you, that's why
`update` takes a `context` object which can be anything. In our case it will contain our `World` for example.

Don't worry if this seems a bit complex, we'll see soon that the benefits of using such system outweigh the costs.

So how do these entities work together? We have `Engine` for that which handles them, so we don't have to do it by hand:

```kotlin
interface Engine<T : Context> {

    /**
     * Adds the given [Entity] to this [Engine].
     */
    fun addEntity(entity: Entity<out EntityType, T>)

    /**
     * Removes the given [Entity] from this [Engine].
     */
    fun removeEntity(entity: Entity<out EntityType, T>)

    /**
     * Updates the [Entity] objects in this [Engine] with the given [context].
     */
    fun update(context: T): Job
}
```
