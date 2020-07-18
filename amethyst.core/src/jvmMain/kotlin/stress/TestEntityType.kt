package stress

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.core.api.UUID

object TestEntityType : EntityType {
    override val name = "test entity"
    override val description = ""
    override val id = UUID.randomUUID()
}
