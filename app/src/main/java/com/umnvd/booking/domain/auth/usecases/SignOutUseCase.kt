package com.umnvd.booking.domain.auth.usecases

import com.umnvd.booking.domain.auth.repositories.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}