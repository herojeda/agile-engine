package com.agile.engine.challenge.core.usecase

import com.agile.engine.challenge.core.repository.images.GetImagesRepository
import com.agile.engine.challenge.core.usecase.auth.RunAutheticated
import com.agile.engine.challenge.core.usecase.images.ObtainAndSaveDetailsForImages
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ReloadCache @Inject constructor(
    private val runAutheticated: RunAutheticated,
    private val getImagesRepository: GetImagesRepository,
    private val obtainAndSaveDetailsForImages: ObtainAndSaveDetailsForImages
) {

    fun execute(): Completable =
        runAutheticated.execute { token ->
            getImagesRepository.execute(token, 1)
        }
            .flatMapCompletable { imagesResponse ->
                obtainAndSaveDetailsForImages.execute(imagesResponse)
                    .andThen(
                        Completable.defer {
                            val remainingPages = imagesResponse.page.inc()..imagesResponse.pageCount
                            Observable.fromIterable(remainingPages)
                                .flatMapCompletable { page ->
                                    runAutheticated.execute { token ->
                                        getImagesRepository.execute(token, page)
                                    }
                                        .flatMapCompletable(obtainAndSaveDetailsForImages::execute)
                                }
                        }
                    )
            }

}