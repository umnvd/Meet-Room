package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import javax.inject.Inject

class EditMeetingRoomUseCase @Inject constructor(
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(params: Params): Result<Unit, AppException> {
        roomsRepository.editRoom(params.uid, params.form)
        return Result.Success(Unit)
    }

    data class Params(
        val uid: String,
        val form: MeetingRoomFormModel,
    )
}