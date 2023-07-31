package com.umnvd.booking.presentation.events.home.schedule.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import java.time.LocalDate

data class MeetingEventScheduleScreenState(
    val date: LocalDate = LocalDate.now(),
    val events: List<MeetingEventModel> = listOf(),
    val loading: Boolean = false,
    val error: AppException? = null,
)
