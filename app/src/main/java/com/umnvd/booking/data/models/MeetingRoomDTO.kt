package com.umnvd.booking.data.models

data class MeetingRoomDTO(
    val uid: String,
    val name: String,
    val address: String,
    val photoUrl: String? = null,
)
