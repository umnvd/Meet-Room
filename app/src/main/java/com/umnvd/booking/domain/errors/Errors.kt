package com.umnvd.booking.domain.errors

sealed class AppException : RuntimeException()

class NetworkException : AppException()

class UnauthorizedException : AppException()

class EmailRequiredException : AppException()

class EmailInvalidException : AppException()

class EmailNotRegisteredException : AppException()

class PasswordRequiredException : AppException()

class PasswordMinLengthException(val minLength: Int) : AppException()

class PasswordInvalidException : AppException()


