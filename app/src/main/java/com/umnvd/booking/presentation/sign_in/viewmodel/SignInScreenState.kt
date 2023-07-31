package com.umnvd.booking.presentation.sign_in.viewmodel

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.domain.AppException

data class SignInScreenState(
    val email: FieldState<String> = FieldState(""),
    val password: FieldState<String> = FieldState(""),
    val loading: Boolean = false,
    val signedIn: Boolean = false,
    val error: AppException? = null,
) {
    val buttonEnabled: Boolean
        get() = email.valid && password.valid && !loading
}
