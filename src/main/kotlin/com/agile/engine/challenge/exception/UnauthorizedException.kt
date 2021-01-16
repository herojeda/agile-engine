package com.agile.engine.challenge.exception

import io.netty.handler.codec.http.HttpResponseStatus

open class UnauthorizedException(
    message: String,
    errorCode: String = HttpResponseStatus.UNAUTHORIZED.reasonPhrase(),
    cause: Throwable? = null
) : ErrorRequestException(
    message = message,
    errorCode = errorCode,
    statusCode = HttpResponseStatus.UNAUTHORIZED.code(),
    cause = cause
)