package com.umnvd.booking.domain.rooms.models

import com.umnvd.booking.domain.AppException

data class MeetingRoomValidationError(
    val name: AppException?,
    val address: AppException?,
)
