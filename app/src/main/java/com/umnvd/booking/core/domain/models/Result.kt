package com.umnvd.booking.core.domain.models

import com.umnvd.booking.domain.AppException

sealed class Result<T> {

    class Success<T>(val value: T) : Result<T>()

    class Error<T>(val error: AppException) : Result<T>()
}