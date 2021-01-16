package com.agile.engine.challenge.entrypoint.contract.response

import io.netty.handler.codec.http.HttpResponseStatus
import java.time.LocalDateTime

data class Response<T>(
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var status: Int = 0,
    var message: String = "",
    var data: T
) {
    constructor(data: T, httpResponseStatus: HttpResponseStatus) :
        this(data = data, message = httpResponseStatus.reasonPhrase(), status = httpResponseStatus.code())
}