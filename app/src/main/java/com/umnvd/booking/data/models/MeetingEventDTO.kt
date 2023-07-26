package com.umnvd.booking.data.models

import java.util.Date

data class MeetingEventDTO(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: Date,
    val endAt: Date,
    val room: MeetingRoomDTO,
    val participants: List<UserDTO>,
)
