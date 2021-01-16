package com.agile.engine.challenge.core.repository.images

import com.agile.engine.challenge.core.repository.contract.response.images.GetImageDetailResponse
import io.reactivex.Single
import io.vertx.core.json.JsonObject

interface GetImageDetailRepository {

    fun execute(token: String, imageId: String): Single<GetImageDetailResponse>
}