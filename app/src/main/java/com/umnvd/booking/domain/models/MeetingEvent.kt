package com.umnvd.booking.domain.models

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class MeetingEvent(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val room: MeetingRoom,
    val participants: List<User>,
)
