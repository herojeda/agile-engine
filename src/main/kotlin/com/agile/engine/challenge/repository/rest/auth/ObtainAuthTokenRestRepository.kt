package com.agile.engine.challenge.repository.rest.auth

import com.agile.engine.challenge.core.repository.auth.ObtainAuthTokenRepository
import com.agile.engine.challenge.core.repository.contract.request.AuthRequest
import com.agile.engine.challenge.core.repository.contract.response.auth.AuthResponse
import com.agile.engine.challenge.infrastructure.model.ClientConfig
import io.reactivex.Single
import io.vertx.reactivex.ext.web.client.WebClient
import javax.inject.Inject

class ObtainAuthTokenRestRepository @Inject constructor(
    private val webClient: WebClient,
    private val clientConfig: ClientConfig
): ObtainAuthTokenRepository {

    companion object {
        private val path = "/auth"
    }

    override fun execute(): Single<AuthResponse> {
        val authRequest = AuthRequest(clientConfig.apiKey)

        return webClient.postAbs("${clientConfig.url}$path")
            .rxSendJson(authRequest)
            .map { response ->
                response.bodyAsJsonObject()
                    .mapTo(AuthResponse::class.java)
            }
    }
}