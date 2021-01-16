package agile.engine.challenge.entrypoint.task

import agile.engine.challenge.base.BaseTest
import com.agile.engine.challenge.entrypoint.task.ReloadCacheTask
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Test

class ReloadCacheTaskTest : BaseTest() {

    @Test
    fun `When execute should return complete`(testContext: VertxTestContext) {

        val task = injector.getInstance(ReloadCacheTask::class.java)

        task
            .execute()
            .subscribe({
                testContext
                    .verify {
                        assert(it == 1L)
                    }
                    .completeNow()
            }, testContext::failNow)
    }
}