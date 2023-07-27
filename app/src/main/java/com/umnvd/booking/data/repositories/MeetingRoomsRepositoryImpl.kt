package com.umnvd.booking.data.repositories

import android.util.Log
import com.umnvd.booking.core.data.AppDispatchers
import com.umnvd.booking.data.mappers.MeetingRoomDtoMapper
import com.umnvd.booking.data.services.MeetingRoomsService
import com.umnvd.booking.domain.models.MeetingRoom
import com.umnvd.booking.domain.models.MeetingRoomForm
import com.umnvd.booking.domain.repositories.MeetingRoomsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeetingRoomsRepositoryImpl @Inject constructor(
    private val meetingRoomsService: MeetingRoomsService,
    private val dispatchers: AppDispatchers,
) : MeetingRoomsRepository {

    override suspend fun room(uid: String): MeetingRoom = withContext(dispatchers.io) {
        val roomDto = meetingRoomsService.getRoom(uid)
        Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
        return@withContext MeetingRoomDtoMapper.dtoToDomain(roomDto)
    }

    override suspend fun allRooms(): List<MeetingRoom> = withContext(dispatchers.io) {
        val roomsDtos = meetingRoomsService.getRooms()
        Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomsDtos.toString())
        return@withContext roomsDtos.map(MeetingRoomDtoMapper::dtoToDomain)
    }

    override suspend fun createRoom(form: MeetingRoomForm): MeetingRoom =
        withContext(dispatchers.io) {
            val roomDto = meetingRoomsService
                .createRoom(MeetingRoomDtoMapper.formDomainToDto(form))
            Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
            return@withContext MeetingRoomDtoMapper.dtoToDomain(roomDto)
        }

    override suspend fun editRoom(uid: String, form: MeetingRoomForm): MeetingRoom =
        withContext(dispatchers.io) {
            val roomDto = meetingRoomsService
                .editRoom(uid, MeetingRoomDtoMapper.formDomainToDto(form))
            Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
            return@withContext MeetingRoomDtoMapper.dtoToDomain(roomDto)
        }

    override suspend fun deleteRoom(uid: String) = withContext(dispatchers.io) {
        meetingRoomsService.deleteRoom(uid)
    }
}