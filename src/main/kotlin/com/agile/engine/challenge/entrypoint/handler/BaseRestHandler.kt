package com.agile.engine.challenge.entrypoint.handler

import com.agile.engine.challenge.entrypoint.contract.response.Response
import com.agile.engine.challenge.exception.RequestValidationException
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.Completable
import io.reactivex.Single
import io.vertx.core.json.Json
import io.vertx.reactivex.ext.web.RoutingContext

interface BaseRestHandler {

    companion object {
        const val CONTENT_TYPE_JSON: String = "application/json"
    }

    fun execute(routingContext: RoutingContext): Completable

    fun <T> mapRequest(ctx: RoutingContext, type: Class<T>): Single<T> =
        Single.fromCallable {
            try {
                ctx.bodyAsJson.mapTo(type)
            } catch (e: Exception) {
                throw RequestValidationException(
                    content = ctx.bodyAsString,
                    message = e.localizedMessage,
                    cause = e
                )
            }
        }

    fun respondWithJson(
        ctx: RoutingContext,
        response: Any,
        status: HttpResponseStatus = HttpResponseStatus.OK
    ) {
        ctx.response()
            .setStatusCode(status.code())
            .putHeader("content-type", "$CONTENT_TYPE_JSON; charset=utf-8")
            .end(Json.encode(Response(data = response, httpResponseStatus = status)))
    }
}