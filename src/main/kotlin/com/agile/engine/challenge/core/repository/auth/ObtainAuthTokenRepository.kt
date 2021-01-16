package com.agile.engine.challenge.core.repository.auth

import com.agile.engine.challenge.core.repository.contract.response.auth.AuthResponse
import io.reactivex.Single

interface ObtainAuthTokenRepository {

    fun execute(): Single<AuthResponse>

}