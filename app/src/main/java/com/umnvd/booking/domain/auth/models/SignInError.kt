package com.umnvd.booking.domain.auth.models

import com.umnvd.booking.domain.AppException

sealed class SignInError {
    data class Validation(
        val email: AppException? = null,
        val password: AppException? = null,
    ) : SignInError()

    object Network : SignInError()
}