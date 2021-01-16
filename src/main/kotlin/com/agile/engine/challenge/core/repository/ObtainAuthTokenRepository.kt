package com.agile.engine.challenge.core.repository

import com.agile.engine.challenge.core.repository.contract.response.AuthResponse
import io.reactivex.Single

interface ObtainAuthTokenRepository {

    fun execute(): Single<AuthResponse>

}