package com.agile.engine.challenge.core.usecase

import com.agile.engine.challenge.core.repository.images.GetImageDetailRepository
import com.agile.engine.challenge.core.repository.images.GetImagesRepository
import com.agile.engine.challenge.core.usecase.auth.RunAutheticated
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ReloadCache @Inject constructor(
    private val runAutheticated: RunAutheticated,
    private val getImagesRepository: GetImagesRepository,
    private val getImageDetailRepository: GetImageDetailRepository
) {

    fun execute(): Completable =
        runAutheticated.execute { token ->
            getImagesRepository.execute(token, 1)
        }
            .flatMapCompletable { imagesResponse ->
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

}