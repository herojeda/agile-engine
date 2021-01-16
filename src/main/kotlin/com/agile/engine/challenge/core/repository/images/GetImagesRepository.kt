package com.agile.engine.challenge.core.repository.images

import com.agile.engine.challenge.core.repository.contract.response.images.GetImagesResponse
import io.reactivex.Single

interface GetImagesRepository {

    fun execute(token: String, page: Int): Single<GetImagesResponse>
}