package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import javax.inject.Inject

class GetMeetingRoomUseCase @Inject constructor(
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(params: Params): Result<MeetingRoomModel, AppException> {
        return try {
            val room = roomsRepository.room(params.uid)
            Result.Success(room)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    data class Params(val uid: String)
}