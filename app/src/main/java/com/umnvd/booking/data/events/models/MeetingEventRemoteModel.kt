package com.umnvd.booking.data.events.models

import com.umnvd.booking.data.rooms.models.MeetingRoomRemoteModel
import com.umnvd.booking.data.users.models.UserRemoteModel

/**
 * @param startAt DD-MM-YYYYTHH:mm:ssZ
 * @param endAt DD-MM-YYYYTHH:mm:ssZ
 */
data class MeetingEventRemoteModel(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: String,
    val endAt: String,
    val room: MeetingRoomRemoteModel,
    val participants: List<UserRemoteModel>,
)
