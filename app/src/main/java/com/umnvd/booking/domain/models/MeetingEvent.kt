package com.umnvd.booking.domain.models

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Immutable
data class MeetingEvent(
    val uid: String,
    val title: String,
    val description: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val room: MeetingRoom,
    val participants: List<User>,
)

val mockMeetingEvent = MeetingEvent(
    uid = "mock_event_1",
    title = "ScumTech",
    description = "Собрание учредителей ScumTech",
    startAt = LocalDateTime.of(
        LocalDate.now(),
        LocalTime.of(1, 0),
    ),
    endAt = LocalDateTime.of(
        LocalDate.now(),
        LocalTime.of(2, 0),
    ),
    room = mockMeetingRoom,
    participants = mockUserList,
)

val mockMeetingEventLongNames = mockMeetingEvent.copy(
    title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
    room = mockMeetingEvent.room.copy(
        name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
    )
)
