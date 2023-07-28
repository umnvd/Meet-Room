package com.umnvd.booking.core.models

import com.umnvd.booking.domain.AppException

data class FieldState<V>(
    val value: V,
    val error: AppException? = null,
) {
    val valid: Boolean
        get() = error == null
}
