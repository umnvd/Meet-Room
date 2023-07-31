package com.umnvd.booking.domain.auth.usecases

import com.umnvd.booking.domain.auth.models.AuthState
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(): AuthState {
        return if (authRepository.isAuthorized())
            AuthState.AUTHORIZED
        else
            AuthState.UNAUTHORIZED
    }
}