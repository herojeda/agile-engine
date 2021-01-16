package com.agile.engine.challenge.infrastructure.routers

import com.agile.engine.challenge.entrypoint.handler.BaseRestHandler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

interface BaseRouter {

    fun getPath(): String
    fun create(): Router

    fun <T : BaseRestHandler> subscribeTo(handler: T): (RoutingContext) -> Unit =
        { routingContext ->
            handler.execute(routingContext)
                .subscribe(
                    {},
                    { error ->
                        routingContext.fail(error)
                    }
                )
        }
}