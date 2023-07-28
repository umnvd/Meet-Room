package com.umnvd.booking.core.models

import com.umnvd.booking.domain.AppException

sealed class Result<T> {

    class Success<T>(val value: T) : Result<T>()

    class Error<T>(error: AppException) : Result<T>()
}