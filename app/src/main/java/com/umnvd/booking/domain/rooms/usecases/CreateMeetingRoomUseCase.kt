package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import javax.inject.Inject

class CreateMeetingRoomUseCase @Inject constructor(
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, AppException> {
        roomsRepository.createRoom(params.form)
        return Result.Success(Unit)
    }

    data class Params(val form: MeetingRoomFormModel)
}