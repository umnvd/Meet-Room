package com.umnvd.booking.presentation.events.home.viewmodel

import java.time.LocalDate

data class MeetingEventsHomeScreenState(
    val selectedDate: LocalDate,
    val loading: Boolean,
)
