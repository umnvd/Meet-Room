package com.umnvd.booking.presentation.rooms

import com.umnvd.booking.core.models.FieldState
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel

data class MeetingRoomFormState(
    val name: FieldState<String>,
    val address: FieldState<String>,
) {

    companion object {
        val Default: MeetingRoomFormState
            get() = MeetingRoomFormState(
                name = FieldState(""),
                address = FieldState(""),
            )
    }
}

fun MeetingRoomFormState.toDomain() = MeetingRoomFormModel(
    name = name.value,
    address = name.value,
)
