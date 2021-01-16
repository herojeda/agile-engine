package com.agile.engine.challenge.core.usecase.auth

import com.agile.engine.challenge.core.repository.auth.ObtainAuthTokenRepository
import com.agile.engine.challenge.exception.UnauthorizedException
import com.agile.engine.challenge.infrastructure.LoggerDelegate
import io.reactivex.Single
import javax.inject.Inject

class RunAutheticated @Inject constructor(
    private val obtainAuthTokenRepository: ObtainAuthTokenRepository
) {

    private val logger by LoggerDelegate()
    private var token: String = ""

    fun <R> execute(
        callback: (String) -> Single<R>
    ): Single<R> {
        return Single.defer {
            if (token.isEmpty()) {
                obtainAuthTokenRepository.execute().map { response ->
                    response.token.also { token = it }
                }
            } else {
                Single.just(token)
            }
        }
            .flatMap(callback)
            .onErrorResumeNext { error ->
                if (error is UnauthorizedException) {
                    logger.warn("RETRY_AUTHENTICATION", error)
                    obtainAuthTokenRepository.execute().map { response ->
                        response.token.also { token = it }
                    }.flatMap(callback)
                } else {
                    Single.error(error)
                }
            }
    }
}