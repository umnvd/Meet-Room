package com.umnvd.booking.domain

import java.time.LocalDateTime

sealed class AppException : RuntimeException()
class UnknownException : AppException()
class NetworkException : AppException()

class UnauthorizedException : AppException()

class EmailRequiredException : AppException()

class EmailInvalidException : AppException()

class EmailNotRegisteredException : AppException()

class PasswordRequiredException : AppException()

class PasswordMinLengthException(val minLength: Int) : AppException()

class PasswordInvalidException : AppException()

// Room form
class RoomNameRequiredException : AppException()
class RoomNameLengthException(val minLength: Int) : AppException()
class RoomAddressRequiredException : AppException()

// Event form
class EventTitleRequiredException : AppException()
class EventTitleLengthException(val minLength: Int) : AppException()
class EventRoomRequiredException : AppException()
class EventParticipantsRequiredException : AppException()
class EventStartIntersectionException(val dateTime: LocalDateTime) : AppException()
class EventEndIntersectionException(val dateTime: LocalDateTime) : AppException()



