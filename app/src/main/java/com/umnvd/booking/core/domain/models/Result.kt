package com.umnvd.booking.core.domain.models

sealed class Result<V, E> {

    data class Success<V, E>(val value: V) : Result<V, E>()

    data class Error<V, E>(val error: E) : Result<V, E>()
}