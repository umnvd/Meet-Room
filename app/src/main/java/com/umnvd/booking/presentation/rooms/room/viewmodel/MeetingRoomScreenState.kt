package com.umnvd.booking.presentation.rooms.room.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState
import com.umnvd.booking.presentation.rooms.common.form.toDomain
import com.umnvd.booking.presentation.rooms.common.form.toForm

data class MeetingRoomScreenState(
    private val room: MeetingRoomModel? = null,
    val formState: MeetingRoomFormState = MeetingRoomFormState(),
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: AppException? = null,
) {
    val saveButtonVisible: Boolean
        get() = room != null && formState.toDomain() != room.toForm()
}
