package com.agile.engine.challenge.infrastructure.routers

import com.agile.engine.challenge.entrypoint.handler.ErrorHandler
import com.agile.engine.challenge.infrastructure.routers.Routes.Companion.HEALTH
import io.vertx.core.json.Json
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.handler.BodyHandler
import javax.inject.Inject

class MainRouter @Inject constructor(
    private val vertx: Vertx,
    private val subRouters: Set<@JvmSuppressWildcards BaseRouter>,
    private val errorHandler: ErrorHandler
) {

    fun create(): Router {

        val router = Router.router(vertx)

        router.apply {
            route().handler(BodyHandler.create())
            router.route("/*").failureHandler(errorHandler)

            subRouters.forEach { mountSubRouter(it.getPath(), it.create()) }
        }

        /*Health Check*/
        router.get(HEALTH).handler { ctx ->
            ctx.response().end(Json.encode(mapOf("status" to "OK")))
        }

        return router
    }
}