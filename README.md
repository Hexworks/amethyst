
![Logo](https://github.com/Hexworks/amethyst/blob/master/amethyst_logo_PREVIEW_shine_darkbg.png)

# Introducing Amethyst

So what is Amethyst, and why is it useful for us? Amethyst is a small library which takes
care of managing your game entities in a nutshell. It is similar to an [Entity Component System](https://en.wikipedia.org/wiki/Entity_component_system)
but it is a bit different in some important aspects.

"So what is a game entity anyway?", you might ask. A game entity can be anything which has its own behavior or state
in your game, from a goblin, to a wall, or even immaterial effects, like shadows. What's important about a game entity
is that it encapsulates all internal state of our goblin for example and it also contains all actions it can
perform. This means that by using this concept we can maintain the [Cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))
of our game objects while still retaining the flexibility.

So how does it work? Let's look at the *SEA*:

## Systems, Entities, Attributes

When working with an ECS, you'll probably see things like Entities, Components, and Systems. In Amethyst we have
Systems, Entities and Attributes. I'm not going into detail about the difference between ECS and SEA, but explain how
SEA works:

Let's say that we want to add a *goblin* to our game. If we think about how things can behave and change over time
we can conclude, that there are essentially two ways: internal, and external. For example a *goblin* can decide
that it wants to explore the dungeon, or just take a look around (**internal change**) or the player can decide to bash
the goblin to a pulpy mass with their club (**external change**). For this Amethyst has `System`s in place:

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

When a change happens over time to an entity (our *goblin* in this example) its state might change. To represent
this, Amethyst gives us `Attribute`s:

```kotlin
/**
 * An [Attribute] represents metadata about an entity which can
 * change over time.
 */
interface Attribute
```

An `Attribute` can be anything which you add to your entity, from health points to stomach contents. What's important
is that `Attribute`s should **never have** behavior, they are supposed to be dumb data structures.

On the other hand, `System`s should never have internal state. These two *important rules* allow us to compose `System`s
and `Attribute`s into `Entity` objects which are flexible, cohesive and safe to use.

So how do these entities work together? We have `Engine` for that which handles them so we don't have to
do it by hand:

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
