package com.umnvd.booking.domain.rooms.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.RoomAddressRequiredException
import com.umnvd.booking.domain.RoomNameLengthException
import com.umnvd.booking.domain.RoomNameRequiredException
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomValidationError
import javax.inject.Inject

class ValidateMeetingRoomFormUseCase @Inject constructor() {

    operator fun invoke(params: Params): Result<Unit, MeetingRoomValidationError> {
        val trimmedName = params.form.name.trim()
        val trimmedAddress = params.form.address.trim()

        val nameError = when {
            trimmedName.isEmpty() -> RoomNameRequiredException()
            trimmedName.length < 3 -> RoomNameLengthException(3)
            else -> null
        }

        val addressError = when {
            trimmedAddress.isEmpty() -> RoomAddressRequiredException()
            else -> null
        }

        return if (nameError != null || addressError != null) {
            Result.Error(
                MeetingRoomValidationError(
                    name = nameError,
                    address = addressError,
                )
            )
        } else Result.Success(value = Unit)
    }

    data class Params(val form: MeetingRoomFormModel)
}