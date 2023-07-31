package com.umnvd.booking.domain.events.models

import com.umnvd.booking.domain.AppException

data class MeetingEventValidationError(
    val title: AppException? = null,
    val startAt: AppException? = null,
    val endAt: AppException? = null,
    val room: AppException? = null,
    val participants: AppException? = null,
)
