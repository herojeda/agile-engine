package com.agile.engine.challenge.exception

import io.netty.handler.codec.http.HttpResponseStatus

open class BadRequestException(
    message: String,
    errorCode: String = HttpResponseStatus.BAD_REQUEST.reasonPhrase(),
    cause: Throwable? = null
) : ErrorRequestException(
    message = message,
    errorCode = errorCode,
    statusCode = HttpResponseStatus.BAD_REQUEST.code(),
    cause = cause
)