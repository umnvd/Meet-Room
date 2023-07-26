package com.umnvd.booking.data.models

import java.util.Date

data class MeetingEventFormDTO(
    val title: String,
    val description: String?,
    val startAt: Date,
    val endAt: Date,
    val roomUid: String,
    val participantsUids: List<String>,
)
