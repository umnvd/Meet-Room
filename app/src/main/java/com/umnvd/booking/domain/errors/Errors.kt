package com.umnvd.booking.domain.errors

sealed class AppException : RuntimeException()

object AuthException : AppException()