package com.umnvd.booking.presentation.auth.viewmodel

import com.umnvd.booking.core.models.FieldState

data class AuthScreenState(
    val email: FieldState<String, EmailFieldError> = FieldState("", null),
    val password: FieldState<String, PasswordFieldError> = FieldState("", null),
    val loading: Boolean = false,
    val signedIn: Boolean = false,
    val networkError: Boolean = false,
) {
    val buttonEnabled: Boolean
        get() = email.valid && password.valid && !loading

    val fieldsEnabled: Boolean
        get() = !loading
}

sealed class EmailFieldError {

    object Required : EmailFieldError()

    object Invalid : EmailFieldError()

    object NotRegistered : EmailFieldError()
}

sealed class PasswordFieldError {

    object Required : PasswordFieldError()

    class TooShort(val minLength: Int) : PasswordFieldError()

    object Invalid : PasswordFieldError()
}
