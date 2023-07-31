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
class RoomNameRequiredException : AppException()
class RoomNameLengthException(val minLength: Int) : AppException()
class RoomAddressRequiredException : AppException()
class EventTitleRequiredException : AppException()
class EventTitleLengthException(val minLength: Int) : AppException()
class EventRoomRequiredException : AppException()
class EventParticipantsRequiredException : AppException()
class EventNegativeDurationException : AppException()
class EventMinDurationException(val minutes: Long) : AppException()
class EventMaxDurationException(val minutes: Long) : AppException()
class EventDifferentDatesException : AppException()
class EventOverlappingException(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime
) : AppException()

class EventStartIntersectionException(val dateTime: LocalDateTime) : AppException()
class EventEndIntersectionException(val dateTime: LocalDateTime) : AppException()



