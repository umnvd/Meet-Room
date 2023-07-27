package com.umnvd.booking.data.models

/**
 * @param startAt dd-MM-yyyy HH:mm:ss
 * @param endAt dd-MM-yyyy HH:mm:ss
 */
data class MeetingEventFormDTO(
    val title: String,
    val description: String?,
    val startAt: String,
    val endAt: String,
    val roomUid: String,
    val participantsUids: List<String>,
)
