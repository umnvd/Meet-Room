package com.umnvd.booking.domain.auth.usecases

import com.umnvd.booking.domain.EmailInvalidException
import com.umnvd.booking.domain.EmailNotRegisteredException
import com.umnvd.booking.domain.EmailRequiredException
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.PasswordInvalidException
import com.umnvd.booking.domain.PasswordMinLengthException
import com.umnvd.booking.domain.PasswordRequiredException
import com.umnvd.booking.domain.auth.models.SignInResult
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val emailRegex = Regex(EMAIL_PATTERN)

    suspend operator fun invoke(params: Params): SignInResult {
        val trimmedEmail = params.email.trim()
        val trimmedPassword = params.password.trim()

        if (trimmedEmail.isEmpty())
            return SignInResult.EmailError(EmailRequiredException())
        if (!emailRegex.matches(trimmedEmail))
            return SignInResult.EmailError(EmailInvalidException())
        if (trimmedPassword.isEmpty())
            return SignInResult.PasswordError(PasswordRequiredException())
        if (trimmedPassword.length < 6)
            return SignInResult.PasswordError(PasswordMinLengthException(6))

        try {
            authRepository.signIn(trimmedEmail, trimmedPassword)
        } catch (e: EmailNotRegisteredException) {
            return SignInResult.EmailError(e)
        } catch (e: PasswordInvalidException) {
            return SignInResult.PasswordError(e)
        } catch (e: NetworkException) {
            return SignInResult.NetworkError
        }
        return SignInResult.Success
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