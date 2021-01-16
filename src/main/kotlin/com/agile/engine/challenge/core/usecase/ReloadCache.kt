package com.agile.engine.challenge.core.usecase

import com.agile.engine.challenge.core.usecase.auth.RunAutheticated
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ReloadCache @Inject constructor(
    val runAutheticated: RunAutheticated
) {

    fun execute(): Completable =
        runAutheticated.execute {
            println("Token: $it")
            Single.just(it)
        }
            .ignoreElement()


}