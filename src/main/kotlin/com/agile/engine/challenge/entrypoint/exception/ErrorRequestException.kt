package com.agile.engine.challenge.entrypoint.exception

import com.agile.engine.challenge.entrypoint.contract.response.Response

abstract class ErrorRequestException(
    override val message: String,
    var statusCode: Int,
    val isLoggable: Boolean = true,
    val errorCode: String,
    cause: Throwable? = null
) : RuntimeException("$errorCode - $message", cause) {
    fun getResponse(): Response<String> = Response(status = statusCode, message = errorCode, data = message)
}