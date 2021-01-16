package agile.engine.challenge

import agile.engine.challenge.base.BaseTest
import com.agile.engine.challenge.infrastructure.routers.Routes.Companion.HEALTH
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class HealthCheckTest: BaseTest() {

    @Test
    fun `HealthCheck test`(testContext: VertxTestContext) {

        // Given
        val givenUri = "$baseURL$HEALTH"

        // When
        val result = webClient.getAbs(givenUri).rxSend()

        // Then
        result.subscribe(
            { response ->
                testContext.verify {
                    val jsonObject: JsonObject = (response.bodyAsJsonObject() ?: JsonObject())
                    Assertions.assertEquals(jsonObject.getString("status"), "OK")
                    testContext.completeNow()
                }
            },
            testContext::failNow
        )
    }
}