package com.umnvd.booking.domain.auth.models

import com.umnvd.booking.domain.AppException

sealed class SignInResult {
    object Success : SignInResult()
    class EmailError(val error: AppException) : SignInResult()
    class PasswordError(val error: AppException) : SignInResult()
    class NetworkError(val error: AppException) : SignInResult()
}
