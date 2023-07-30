package com.umnvd.booking.data.rooms.models

data class MeetingRoomRemoteModel(
    val uid: String,
    val name: String,
    val address: String,
    val photoUrl: String? = null,
    val createdAt: String,
)
