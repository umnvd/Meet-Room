package com.umnvd.booking.presentation.mappers

import com.umnvd.booking.domain.models.MeetingRoomForm
import com.umnvd.booking.presentation.models.MeetingRoomFormState

object MeetingRoomUiMapper {

    fun formStateToDomain(obj: MeetingRoomFormState) = MeetingRoomForm(
        name = obj.name.value,
        address = obj.name.value,
    )
}