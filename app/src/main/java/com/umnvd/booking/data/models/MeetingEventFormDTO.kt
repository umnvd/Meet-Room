package com.umnvd.booking.data.models

/**
 * @param startAt DD-MM-YYYYTHH:mm:ssZ
 * @param endAt DD-MM-YYYYTHH:mm:ssZ
 */
data class MeetingEventFormDTO(
    val title: String,
    val description: String?,
    val startAt: String,
    val endAt: String,
    val roomUid: String,
    val participantsUids: List<String>,
)
