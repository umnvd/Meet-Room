package com.umnvd.booking.domain.repositories

import com.umnvd.booking.domain.models.MeetingRoom
import com.umnvd.booking.domain.models.MeetingRoomForm

interface MeetingRoomsRepository {

    suspend fun room(uid: String): MeetingRoom

    suspend fun allRooms(): List<MeetingRoom>

    suspend fun createRoom(form: MeetingRoomForm): MeetingRoom

    suspend fun editRoom(uid: String, form: MeetingRoomForm): MeetingRoom

    suspend fun deleteRoom(uid: String): Unit
}