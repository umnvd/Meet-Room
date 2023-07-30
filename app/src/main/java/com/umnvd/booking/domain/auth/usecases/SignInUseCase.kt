package com.umnvd.booking.domain.auth.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.EmailInvalidException
import com.umnvd.booking.domain.EmailNotRegisteredException
import com.umnvd.booking.domain.EmailRequiredException
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.PasswordInvalidException
import com.umnvd.booking.domain.PasswordMinLengthException
import com.umnvd.booking.domain.PasswordRequiredException
import com.umnvd.booking.domain.auth.models.SignInError
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    private val emailRegex = Regex(EMAIL_PATTERN)

    suspend operator fun invoke(params: Params): Result<Unit, SignInError> {
        val trimmedEmail = params.email.trim()
        val trimmedPassword = params.password.trim()

        val emailError = if (trimmedEmail.isEmpty())
            EmailRequiredException()
        else if (!emailRegex.matches(trimmedEmail))
            EmailInvalidException()
        else null

        val passwordError = if (trimmedPassword.isEmpty())
            PasswordRequiredException()
        else if (trimmedPassword.length < 6)
            PasswordMinLengthException(6)
        else null

        if (emailError != null || passwordError != null) {
            return Result.Error(
                SignInError.Validation(
                    email = emailError,
                    password = passwordError,
                )
            )
        }

        try {
            authRepository.signIn(trimmedEmail, trimmedPassword)
        } catch (e: EmailNotRegisteredException) {
            return Result.Error(SignInError.Validation(email = e))
        } catch (e: PasswordInvalidException) {
            return Result.Error(SignInError.Validation(password = e))
        } catch (e: NetworkException) {
            return Result.Error(SignInError.Network)
        }
        return Result.Success(Unit)
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