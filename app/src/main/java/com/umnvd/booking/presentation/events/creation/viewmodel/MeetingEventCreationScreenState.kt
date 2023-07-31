package com.umnvd.booking.presentation.events.creation.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import javax.annotation.concurrent.Immutable

@Immutable
data class MeetingEventCreationScreenState(
    val formState: MeetingEventFormState = MeetingEventFormState(),
    val loading: Boolean = false,
    val allRooms: List<MeetingRoomModel> = listOf(),
    val allUsers: List<UserModel> = listOf(),
    val created: Boolean = false,
    val error: AppException? = null,
)
