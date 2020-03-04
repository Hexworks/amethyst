
![Logo](https://github.com/Hexworks/amethyst/blob/master/amethyst_logo_PREVIEW_shine_darkbg.png)

# Introducing Amethyst

*Amethyst* is a small library which enables you to modularize your application by letting you decompose them into **Systems**, **Entities** and **Attributes** in an easy-to-use manner. It is similar to an [Entity Component System](https://en.wikipedia.org/wiki/Entity_component_system) but it is a bit different in some important aspects.

The core abstraction in *Amethyst* is the **Entity** which encapsulates the internal state of our business objects and also their related operations. By using this concept we can maintain the [Cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))
of our business objects while still retaining the flexibility.

`So how does this work?`, you might ask. Let's take a look at a simple example.

## Systems, Entities, Attributes

Let's say that we're creating a game with mythical creatures and we want to add a *goblin* to our game. If we think about how things can behave and change over time we can conclude, that there are essentially two ways: *internal*, and *external*. For example a *goblin* can decide
that it wants to explore a dungeon, or just take a look around (**internal change**) or the player can decide to bash the goblin to a pulpy mass with their club (**external change**). For this Amethyst has `System`s in place:

```kotlin
/**
 * A [System] is responsible for updating the internal state
 * ([Attribute]s) of an [Entity].
 */
interface System<C : Context>
```

There are several types of `System`s, and for our discussion here we need to know about `Behavior`:

```kotlin
/**
 * A [Behavior] is a [System] which performs actions autonomously
 * with entities whenever they are [update]d.
 */
interface Behavior<C : Context> : System<C>
```

which lets our *goblin* interact with the world and `Facet`:

```kotlin
/**
 * A [Facet] is a [System] which performs actions based on the
 * [Command]s they receive.
 */
interface Facet<C : Context> : System<C>
```

which lets whe **world** interact with our *goblin*.

> There is also `Actor` which combines `Facet` and `Behavior`. We'll talk about it later.

When a change happens over time to an entity (our *goblin* in this example) its state might change. To represent this, *Amethyst* gives us `Attribute`s:

```kotlin
/**
 * An [Attribute] represents metadata about an entity which can
 * change over time.
 */
interface Attribute
```

An `Attribute` can be anything which you add to your *entity*, from health points to stomach contents. What's important is that `Attribute`s should **never have** behavior, they are supposed to be dumb data structures.

On the other hand, `System`s should never have internal state. These two *important rules* allow us to compose `System`s
and `Attribute`s into `Entity` objects which are flexible, cohesive and safe to use.

So how do these entities work together? We have `Engine` for that which handles them so we don't have to do it by hand:

```kotlin
interface Engine<T : Context> {

    /**
     * Adds the given [Entity] to this [Engine].
     */
    fun addEntity(entity: Entity<EntityType, T>)

    /**
     * Removes the given [Entity] from this [Engine].
     */
    fun removeEntity(entity: Entity<EntityType, T>)

    /**
     * Updates the [Entity] objects in this [Engine] with the given [context].
     */
    fun update(context: T)
}
```


