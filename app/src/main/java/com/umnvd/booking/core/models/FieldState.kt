package com.umnvd.booking.core.models

data class FieldState<V, E>(
    val value: V,
    val error: E? = null,
) {
    val valid: Boolean
        get() = error == null
}
