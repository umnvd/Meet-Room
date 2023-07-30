package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import javax.inject.Inject

class GetMeetingRoomListUseCase @Inject constructor(
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(): Result<List<MeetingRoomModel>, AppException> {
        return try {
            val rooms = roomsRepository
                .allRooms()
                .sortedBy(MeetingRoomModel::createdAt)
            Result.Success(rooms)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }
}