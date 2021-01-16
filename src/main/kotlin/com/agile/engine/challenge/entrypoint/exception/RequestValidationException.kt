package com.agile.engine.challenge.entrypoint.exception

open class RequestValidationException(
    message: String,
    content: String,
    errorCode: String = "REQUEST_VALIDATION_ERROR",
    cause: Throwable?
) : BadRequestException(
    message = "$message - Body: $content",
    errorCode = errorCode,
    cause = cause
)