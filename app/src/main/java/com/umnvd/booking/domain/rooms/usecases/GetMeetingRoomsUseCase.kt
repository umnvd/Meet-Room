package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import javax.inject.Inject

class GetMeetingRoomsUseCase @Inject constructor(
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(): Result<List<MeetingRoomModel>> {
        return try {
            val rooms = roomsRepository.allRooms()
            Result.Success(rooms)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }
}