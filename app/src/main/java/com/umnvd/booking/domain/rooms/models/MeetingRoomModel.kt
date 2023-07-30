package com.umnvd.booking.domain.rooms.models

import java.time.LocalDateTime

data class MeetingRoomModel(
    val uid: String,
    val name: String,
    val address: String,
    val createdAt: LocalDateTime,
)


