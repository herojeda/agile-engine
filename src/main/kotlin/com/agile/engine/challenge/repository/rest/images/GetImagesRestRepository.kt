package com.agile.engine.challenge.repository.rest.images

import com.agile.engine.challenge.core.repository.contract.response.images.GetImagesResponse
import com.agile.engine.challenge.core.repository.images.GetImagesRepository
import com.agile.engine.challenge.infrastructure.model.ClientConfig
import io.reactivex.Single
import io.vertx.reactivex.ext.web.client.WebClient
import javax.inject.Inject


class GetImagesRestRepository @Inject constructor(
    private val webClient: WebClient,
    private val clientConfig: ClientConfig
): GetImagesRepository {

    companion object {
        val path = "/images"
    }

    override fun execute(token: String, page: Int): Single<GetImagesResponse> =
        webClient.getAbs("${clientConfig.url}$path")
            .putHeader("Authorization", "Bearer $token")
            .addQueryParam("page", "$page")
            .rxSend()
            .map { response ->
                response.bodyAsJsonObject()
                    .mapTo(GetImagesResponse::class.java)
            }

}