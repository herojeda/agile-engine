package com.agile.engine.challenge.core.usecase

import com.agile.engine.challenge.core.repository.images.GetImagesRepository
import com.agile.engine.challenge.core.usecase.auth.RunAutheticated
import io.reactivex.Completable
import javax.inject.Inject

class ReloadCache @Inject constructor(
    val runAutheticated: RunAutheticated,
    val getImagesRepository: GetImagesRepository
) {

    fun execute(): Completable =
        runAutheticated.execute { token ->
            getImagesRepository.execute(token, 1)
        }
            .flatMapCompletable {
                println(it)
                Completable.complete()
            }

}