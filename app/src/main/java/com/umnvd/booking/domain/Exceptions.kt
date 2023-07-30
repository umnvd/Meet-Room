package com.umnvd.booking.domain

sealed class AppException : RuntimeException()

class NetworkException : AppException()

class UnauthorizedException : AppException()

class EmailRequiredException : AppException()

class EmailInvalidException : AppException()

class EmailNotRegisteredException : AppException()

class PasswordRequiredException : AppException()

class PasswordMinLengthException(val minLength: Int) : AppException()

class PasswordInvalidException : AppException()

// Room form
class RoomNameRequiredException() : AppException()
class RoomNameLengthException(val minLength: Int) : AppException()
class RoomAddressRequiredException() : AppException()


