package com.umnvd.booking.presentation.rooms.room.models

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.domain.rooms.models.MeetingRoomFormModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel

data class MeetingRoomFormState(
    val name: FieldState<String> = FieldState(""),
    val address: FieldState<String> = FieldState(""),
)

fun MeetingRoomFormState.toDomain() = MeetingRoomFormModel(
    name = name.value,
    address = address.value,
)

fun MeetingRoomModel.toFormState() = MeetingRoomFormState(
    name = FieldState(name),
    address = FieldState(address),
)

fun MeetingRoomModel.toForm() = MeetingRoomFormModel(
    name = name,
    address = address,
)
