package com.umnvd.booking.domain.auth.models

import com.umnvd.booking.domain.AppException

sealed class SignInResult {
    object Success : SignInResult()
    class EmailError(val error: AppException) : SignInResult()
    class PasswordError(val error: AppException) : SignInResult()
    object NetworkError : SignInResult()
}

//sealed class SignInError(val error: AppException) {
//    class Email(error: AppException) : SignInError(error)
//    class Password(error: AppException) : SignInError(error)
//    class Common(error: AppException) : SignInError(error)
//}
