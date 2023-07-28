package com.umnvd.booking.util

import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PreviewMocks() {
    class Users {
        val user = UserModel(
            uid = "mock_user_1",
            nickname = "felafelq1",
            fullName = "Дмитрий Умнов",
            photoUrl = "https://i.pravatar.cc/300?img=11",
        )

        val usersList = listOf(
            user,
            UserModel(
                uid = "mock_user_2",
                nickname = "elsagate2022",
                fullName = "Елизавета Гаврилова",
                photoUrl = "https://i.pravatar.cc/300?img=22",
            ),
            UserModel(
                uid = "mock_user_3",
                nickname = "VladIsLove3",
                fullName = "Владислав Карасёв",
                photoUrl = "https://i.pravatar.cc/300?img=3",
            ),
            UserModel(
                uid = "mock_user_33",
                nickname = "Test",
                fullName = "Тестов Тест",
                photoUrl = "https://i.pravatar.cc/300?img=33",
            ),
        )
    }

    class MeetingRooms {
        val room = MeetingRoomModel(
            uid = "mock_room_1",
            name = "Офис ScumTech",
            address = "ул Димитрова, д. 3",
        )

        val roomLongNames = room.copy(
            name = LOREM,
            address = LOREM,
        )

        val roomsList = listOf(
            room,
            room.copy(uid = "mock_room_2"),
            room.copy(uid = "mock_room_3"),
        )
    }

    class MeetingEvents {
        val event = MeetingEventModel(
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
            room = MeetingRooms().room,
            participants = Users().usersList,
        )

        val eventLongNames = event.copy(
            title = LOREM,
            room = MeetingRooms().roomLongNames,
        )

        val eventsList = listOf(
            event,
            event.copy(
                startAt = event.startAt.plusMinutes(90L),
                endAt = event.startAt.plusMinutes(120L),
            ),
            event.copy(
                startAt = event.startAt.plusHours(4),
                endAt = event.startAt.plusHours(6),
            ),
            event.copy(
                startAt = LocalDateTime.of(
                    LocalDate.now(),
                    LocalTime.of(0, 0),
                ),
                endAt = LocalDateTime.of(
                    LocalDate.now(),
                    LocalTime.of(1, 0),
                ),
            ),
            event.copy(
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
    }

    companion object {
        private const val LOREM =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"
    }
}