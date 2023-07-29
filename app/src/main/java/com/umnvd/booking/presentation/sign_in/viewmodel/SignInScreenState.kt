package com.umnvd.booking.presentation.sign_in.viewmodel

import com.umnvd.booking.core.models.FieldState

data class SignInScreenState(
    val email: FieldState<String> = FieldState(""),
    val password: FieldState<String> = FieldState(""),
    val loading: Boolean = false,
    val signedIn: Boolean = false,
    val networkError: Boolean = false,
) {
    val buttonEnabled: Boolean
        get() = email.valid && password.valid && !loading

    val fieldsEnabled: Boolean
        get() = !loading
}
