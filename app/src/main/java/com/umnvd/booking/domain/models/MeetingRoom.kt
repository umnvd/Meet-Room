package com.umnvd.booking.domain.models

data class MeetingRoom(
    val uid: String,
    val name: String,
    val address: String,
    val photoUrl: String? = null,
)

val mockMeetingRoom = MeetingRoom(
    uid = "mock_room_1",
    name = "Офис ScumTech",
    address = "ул Димитрова, д. 3",
    photoUrl = "https://www.justcoglobal.com/wp-content/uploads/2022/06/Just-Inspire-1.jpg",
)

val mockMeetingRoomLongNames = mockMeetingRoom.copy(
    name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
    address = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
)
