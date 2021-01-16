package agile.engine.challenge.base

import com.google.inject.Guice
import com.google.inject.Injector
import com.agile.engine.challenge.infrastructure.ConfigLoader
import com.agile.engine.challenge.infrastructure.JsonMapperConfig
import com.agile.engine.challenge.infrastructure.MainModule
import com.agile.engine.challenge.infrastructure.MainVerticle
import com.agile.engine.challenge.infrastructure.model.SystemConfig
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.client.WebClient
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
abstract class BaseTest {

    companion object {
        lateinit var injector: Injector
        lateinit var webClient: WebClient
        lateinit var baseURL: String

        @JvmStatic
        @BeforeAll
        fun deployApp(vertx: Vertx, testContext: VertxTestContext) {
            JsonMapperConfig.applyConfig()
            val config = ConfigLoader(vertx).read().blockingGet()
            injector = Guice.createInjector(MainModule(vertx, config))

            val serverPath = injector.getInstance(SystemConfig::class.java).basePath
            val serverPort = "8080"

            baseURL = "http://localhost:$serverPort$serverPath"

            webClient = injector.getInstance(WebClient::class.java)

            val mainVerticle = injector.getInstance(MainVerticle::class.java)
            vertx.rxDeployVerticle(mainVerticle)
                .subscribe({ testContext.completeNow() }, testContext::failNow)

            testContext.awaitCompletion(4, TimeUnit.SECONDS)
        }
    }
}