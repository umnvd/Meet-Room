package com.umnvd.booking.data.rooms.repositories

import android.util.Log
import com.umnvd.booking.data.rooms.mappers.MeetingRoomRemoteModelMapper
import com.umnvd.booking.data.rooms.services.MeetingRoomsService
import com.umnvd.booking.di.IoDispatcher
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeetingRoomsRepositoryImpl @Inject constructor(
    private val meetingRoomsService: MeetingRoomsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MeetingRoomsRepository {

    override suspend fun room(uid: String): MeetingRoomModel = withContext(ioDispatcher) {
        val roomDto = meetingRoomsService.getRoom(uid)
        Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
        return@withContext MeetingRoomRemoteModelMapper.dtoToDomain(roomDto)
    }

    override suspend fun allRooms(): List<MeetingRoomModel> = withContext(ioDispatcher) {
        val roomsDtos = meetingRoomsService.getRooms()
        Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomsDtos.toString())
        return@withContext roomsDtos.map(MeetingRoomRemoteModelMapper::dtoToDomain)
    }

    override suspend fun createRoom(form: MeetingRoomFormModel): MeetingRoomModel =
        withContext(ioDispatcher) {
            val roomDto = meetingRoomsService
                .createRoom(MeetingRoomRemoteModelMapper.formDomainToDto(form))
            Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
            return@withContext MeetingRoomRemoteModelMapper.dtoToDomain(roomDto)
        }

    override suspend fun editRoom(uid: String, form: MeetingRoomFormModel): MeetingRoomModel =
        withContext(ioDispatcher) {
            val roomDto = meetingRoomsService
                .editRoom(uid, MeetingRoomRemoteModelMapper.formDomainToDto(form))
            Log.d(this@MeetingRoomsRepositoryImpl.javaClass.simpleName, roomDto.toString())
            return@withContext MeetingRoomRemoteModelMapper.dtoToDomain(roomDto)
        }

    override suspend fun deleteRoom(uid: String) = withContext(ioDispatcher) {
        meetingRoomsService.deleteRoom(uid)
    }
}