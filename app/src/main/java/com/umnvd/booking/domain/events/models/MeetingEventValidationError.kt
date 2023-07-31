package com.umnvd.booking.domain.events.models

import com.umnvd.booking.domain.AppException

data class MeetingEventValidationError(
    val title: AppException? = null,
    val startDate: AppException? = null,
    val startTime: AppException? = null,
    val endDate: AppException? = null,
    val endTime: AppException? = null,
    val duration: AppException? = null,
    val room: AppException? = null,
    val participants: AppException? = null,
)
