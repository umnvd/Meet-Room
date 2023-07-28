package com.umnvd.booking.domain.auth.usecases

import com.umnvd.booking.domain.errors.EmailInvalidException
import com.umnvd.booking.domain.errors.EmailRequiredException
import com.umnvd.booking.domain.errors.PasswordMinLengthException
import com.umnvd.booking.domain.errors.PasswordRequiredException
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val emailRegex = Regex(EMAIL_PATTERN)

    suspend operator fun invoke(params: Params) {
        val trimmedEmail = params.email.trim()
        val trimmedPassword = params.password.trim()

        if (trimmedEmail.isEmpty()) throw EmailRequiredException()
        if (!emailRegex.matches(trimmedEmail)) throw EmailInvalidException()
        if (trimmedPassword.isEmpty()) throw PasswordRequiredException()
        if (trimmedPassword.length < 6) throw PasswordMinLengthException(6)
        authRepository.signIn(trimmedEmail, trimmedPassword)
    }

    private companion object {
        private const val EMAIL_PATTERN = ("^(?=.{1,64}@)[A-Za-z0-9_-]+"
                + "(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+"
                + "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    }

    data class Params(
        val email: String,
        val password: String,
    )
}