package com.umnvd.booking.domain.events.models

import androidx.compose.runtime.Immutable
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import java.time.LocalDateTime

@Immutable
data class MeetingEventModel(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val room: MeetingRoomModel,
    val participants: List<UserModel>,
)
