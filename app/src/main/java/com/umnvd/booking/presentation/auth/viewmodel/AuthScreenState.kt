package com.umnvd.booking.presentation.auth.viewmodel

import com.umnvd.booking.core.models.FieldState

data class AuthScreenState(
    val login: FieldState<String, String> = FieldState("test", null),
    val password: FieldState<String, String> = FieldState("", null),
    val loading: Boolean = false,
    val signedIn: Boolean = false,
) {
    val buttonEnabled: Boolean
        get() = login.valid && password.valid && !loading

    val fieldsEnabled: Boolean
        get() = !loading
}
