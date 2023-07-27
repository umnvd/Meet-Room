package com.umnvd.booking.presentation.models

import com.umnvd.booking.core.models.FieldState

data class MeetingRoomFormState(
    val name: FieldState<String, String>,
    val address: FieldState<String, String>,
) {

    companion object {
        val Default: MeetingRoomFormState
            get() = MeetingRoomFormState(
                name = FieldState(""),
                address = FieldState(""),
            )
    }
}
