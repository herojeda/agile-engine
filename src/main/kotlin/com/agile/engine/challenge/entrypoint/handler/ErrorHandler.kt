package com.agile.engine.challenge.entrypoint.handler

import com.agile.engine.challenge.entrypoint.contract.response.Response
import com.agile.engine.challenge.exception.ErrorRequestException
import com.agile.engine.challenge.infrastructure.LoggerDelegate
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.exceptions.CompositeException
import io.vertx.core.Handler
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.Json
import io.vertx.reactivex.ext.web.RoutingContext

class ErrorHandler : Handler<RoutingContext> {

    private val logger by LoggerDelegate()

    override fun handle(event: RoutingContext) {
        val response = event.response()
        val failure: Throwable = event.failure()

        val cause = if (failure is CompositeException) failure.cause.cause else failure
        val bodyError: Response<out Any> =
            if (cause is ErrorRequestException) cause.getResponse() else getGenericError()

        response.setStatusCode(bodyError.status)
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
            .end(Json.encode(bodyError))

        if (failure !is ErrorRequestException || failure.isLoggable) {
            logger.error(
                "ERROR_PROCESSING_REQUEST - " +
                    "${event.request().method()} - " +
                    "${event.request().path()} - " +
                    "${failure.javaClass.name} - " +
                    "${failure.message} - " +
                    "${event.bodyAsString} - ",
                failure
            )
        }
    }

    private fun getGenericError() = Response(
        status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
        data = "Internal server error",
        message = "Internal server error"
    )
}