package com.agile.engine.challenge.core.usecase.images

import com.agile.engine.challenge.core.repository.contract.response.images.GetImagesResponse
import com.agile.engine.challenge.core.repository.images.GetImageDetailRepository
import com.agile.engine.challenge.core.usecase.auth.RunAutheticated
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ObtainAndSaveDetailsForImages @Inject constructor(
    private val runAutheticated: RunAutheticated,
    private val getImageDetailRepository: GetImageDetailRepository
) {

    fun execute(imagesResponse: GetImagesResponse): Completable =
        Observable.fromIterable(imagesResponse.pictures)
            .flatMapCompletable { picture ->
                runAutheticated.execute { token ->
                    getImageDetailRepository.execute(token, picture.id)
                }
                    .flatMapCompletable {
                        println(it)
                        Completable.complete()
                    }
            }
}