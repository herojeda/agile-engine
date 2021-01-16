package com.agile.engine.challenge.infrastructure

import com.google.inject.Guice
import io.vertx.core.logging.SLF4JLogDelegateFactory
import io.vertx.reactivex.core.Vertx
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("ACCOUNTING-ERP-CONNECTOR-APPLICATION")
    System.setProperty("vertx.logger-delegate-factory-class-name", SLF4JLogDelegateFactory::class.java.name)

    val vertx = Vertx.vertx()

    val config = ConfigLoader(vertx).read().blockingGet()
    val injector = Guice.createInjector(MainModule(vertx, config))
    val main = injector.getInstance(MainVerticle::class.java)

    vertx.deployVerticle(main) { result ->
        if (result.succeeded()) {
            logger.info("Application started")
        } else {
            logger.error("Could not start application", result.cause())
        }
    }

    Runtime.getRuntime().addShutdownHook(
        Thread {
            logger.info("Closing application")
            vertx.close()
        }
    )
}
