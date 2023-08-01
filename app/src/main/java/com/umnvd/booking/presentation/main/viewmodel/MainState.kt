package com.umnvd.booking.presentation.main.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import javax.annotation.concurrent.Immutable

@Immutable
data class MainState(
    val currentUser: UserModel? = null,
    val events: List<MeetingEventModel> = listOf(),
    val rooms: List<MeetingRoomModel> = listOf(),
    val users: List<UserModel> = listOf(),
    val loading: Boolean = false,
    val error: AppException? = null,
)
