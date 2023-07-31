package com.umnvd.booking.presentation.rooms.creation.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState

data class MeetingRoomCreationScreenState(
    val formState: MeetingRoomFormState = MeetingRoomFormState(),
    val loading: Boolean = false,
    val created: Boolean = false,
    val error: AppException? = null,
)
