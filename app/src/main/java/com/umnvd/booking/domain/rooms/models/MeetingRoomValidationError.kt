package com.umnvd.booking.domain.rooms.models

import com.umnvd.booking.domain.AppException

data class MeetingRoomValidationError(
    val name: AppException? = null,
    val address: AppException? = null,
)
