package com.umnvd.booking.data.models

/**
 * @param startAt dd-MM-yyyy HH:mm:ss
 * @param endAt dd-MM-yyyy HH:mm:ss
 */
data class MeetingEventDTO(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: String,
    val endAt: String,
    val room: MeetingRoomDTO,
    val participants: List<UserDTO>,
)
