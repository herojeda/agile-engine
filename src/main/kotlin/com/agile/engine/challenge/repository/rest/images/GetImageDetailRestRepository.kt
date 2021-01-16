package com.agile.engine.challenge.repository.rest.images

import com.agile.engine.challenge.core.repository.contract.response.images.GetImageDetailResponse
import com.agile.engine.challenge.core.repository.images.GetImageDetailRepository
import com.agile.engine.challenge.infrastructure.model.ClientConfig
import io.reactivex.Single
import io.vertx.reactivex.ext.web.client.WebClient
import javax.inject.Inject

class GetImageDetailRestRepository @Inject constructor(
    private val webClient: WebClient,
    private val clientConfig: ClientConfig
): GetImageDetailRepository {

    companion object {
        private val path = "/images"
    }

    override fun execute(token: String, imageId: String): Single<GetImageDetailResponse> =
        webClient.getAbs("${clientConfig.url}$path/$imageId")
            .putHeader("Authorization", "Bearer $token")
            .rxSend()
            .map { response ->
                response.bodyAsJsonObject()
                    .mapTo(GetImageDetailResponse::class.java)
            }

}