package com.umnvd.booking.presentation.events.schedule.calendar.viewmodel

import com.umnvd.booking.domain.AppException
import java.time.LocalDate

data class MeetingEventCalendarScreenState(
    val eventDays: Set<LocalDate> = hashSetOf(),
    val loading: Boolean = false,
    val error: AppException? = null,
)
