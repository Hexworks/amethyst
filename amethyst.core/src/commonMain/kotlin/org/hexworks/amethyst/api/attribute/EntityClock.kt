package org.hexworks.amethyst.api.attribute

import kotlinx.datetime.Clock
import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.core.api.UUID

data class EntityClock(
    override val id: UUID,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val logicalTime: Long = 100
) : Attribute, Comparable<EntityClock> {
    override fun compareTo(other: EntityClock): Int {
        val logicalResult = logicalTime.compareTo(other.logicalTime)
        return if (logicalResult == 0) {
            createdAt.compareTo(other.createdAt)
        } else logicalResult
    }
}
