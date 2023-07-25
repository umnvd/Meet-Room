package com.umnvd.booking.domain.usecases

import com.umnvd.booking.domain.repositories.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signIn(email, password)
    }
}