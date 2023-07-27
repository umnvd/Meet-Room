package com.umnvd.booking.util

import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.MeetingRoom
import com.umnvd.booking.domain.models.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private const val LOREM =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"

val mockUser = User(
    uid = "mock_user_1",
    nickname = "felafelq1",
    fullName = "Дмитрий Умнов",
    photoUrl = "https://i.pravatar.cc/300?img=11",
)

val mockUserList = listOf(
    mockUser,
    User(
        uid = "mock_user_2",
        nickname = "elsagate2022",
        fullName = "Елизавета Гаврилова",
        photoUrl = "https://i.pravatar.cc/300?img=22",
    ),
    User(
        uid = "mock_user_3",
        nickname = "VladIsLove3",
        fullName = "Владислав Карасёв",
        photoUrl = "https://i.pravatar.cc/300?img=3",
    ),
    User(
        uid = "mock_user_33",
        nickname = "Test",
        fullName = "Тестов Тест",
        photoUrl = "https://i.pravatar.cc/300?img=33",
    ),
)

val mockMeetingRoom = MeetingRoom(
    uid = "mock_room_1",
    name = "Офис ScumTech",
    address = "ул Димитрова, д. 3",
)

val mockMeetingRoomList = listOf(
    mockMeetingRoom,
    mockMeetingRoom.copy(uid = "mock_room_2"),
    mockMeetingRoom.copy(uid = "mock_room_3"),
)

val mockMeetingRoomLongNames = mockMeetingRoom.copy(
    name = LOREM,
    address = LOREM,
)

val mockMeetingEvent = MeetingEvent(
    uid = "mock_event_1",
    title = "ScumTech",
    description = "Собрание учредителей ScumTech",
    startAt = LocalDateTime.of(
        LocalDate.of(2023, 7, 28),
        LocalTime.of(1, 0),
    ),
    endAt = LocalDateTime.of(
        LocalDate.of(2023, 7, 28),
        LocalTime.of(2, 0),
    ),
    room = mockMeetingRoom,
    participants = mockUserList,
)

val mockMeetingEventLongNames = mockMeetingEvent.copy(
    title = LOREM,
    room = mockMeetingRoomLongNames,
)

val mockMeetingEventList = listOf(
    mockMeetingEvent,
    mockMeetingEvent.copy(
        startAt = mockMeetingEvent.startAt.plusMinutes(90L),
        endAt = mockMeetingEvent.startAt.plusMinutes(120L),
    ),
    mockMeetingEvent.copy(
        startAt = mockMeetingEvent.startAt.plusHours(4),
        endAt = mockMeetingEvent.startAt.plusHours(6),
    ),
    mockMeetingEvent.copy(
        startAt = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.of(0, 0),
        ),
        endAt = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.of(1, 0),
        ),
    ),
    mockMeetingEvent.copy(
        startAt = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.of(23, 0),
        ),
        endAt = LocalDateTime.of(
            LocalDate.now().plusDays(1),
            LocalTime.of(0, 0),
        ),
    ),
)