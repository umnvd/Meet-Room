package com.umnvd.booking.domain.rooms.repositories

import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel

interface MeetingRoomsRepository {
    suspend fun room(uid: String): MeetingRoomModel
    suspend fun allRooms(): List<MeetingRoomModel>
    suspend fun createRoom(form: MeetingRoomFormModel): MeetingRoomModel
    suspend fun editRoom(uid: String, form: MeetingRoomFormModel): MeetingRoomModel
    suspend fun deleteRoom(uid: String)
}