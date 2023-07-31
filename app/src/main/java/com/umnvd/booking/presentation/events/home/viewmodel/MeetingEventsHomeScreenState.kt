package com.umnvd.booking.presentation.events.home.viewmodel

import java.time.LocalDate

data class MeetingEventsHomeScreenState(
    val selectedDate: LocalDate = LocalDate.now(),
    val events: List<MeetingEventsHomeScreenState> = listOf(),
    val loading: Boolean = false,
    val sync: Boolean = false,
)
