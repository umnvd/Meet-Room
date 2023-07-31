package com.umnvd.booking.presentation.events.event.viewmodel

import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.form.toDomain
import com.umnvd.booking.presentation.events.common.form.toForm

data class MeetingEventScreenState(
    val event: MeetingEventModel? = null,
    val formState: MeetingEventFormState = MeetingEventFormState(),
    val loading: Boolean = false,
    val allRooms: List<MeetingRoomModel> = listOf(),
    val allUsers: List<UserModel> = listOf(),
    val saved: Boolean = false,
) {
    val saveButtonVisible: Boolean
        get() = event != null && formState.toDomain() != event.toForm()
}
