package com.umnvd.booking.data.models

/**
 * @param startAt DD-MM-YYYYTHH:mm:ssZ
 * @param endAt DD-MM-YYYYTHH:mm:ssZ
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
