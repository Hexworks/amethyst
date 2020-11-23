package stress

import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object TestFacet : BaseFacet<TestContext, TestMessage>(TestMessage::class) {

    override suspend fun receive(message: TestMessage): Response {
        return Pass
    }
}
