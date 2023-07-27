package com.umnvd.booking.domain.models

import java.time.LocalDateTime

data class MeetingEventForm(
    val title: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val room: MeetingRoom?,
    val participants: List<User>,
)
