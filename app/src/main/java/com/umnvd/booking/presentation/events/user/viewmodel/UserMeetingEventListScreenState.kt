package com.umnvd.booking.presentation.events.user.viewmodel

import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.users.models.UserModel
import javax.annotation.concurrent.Immutable

@Immutable
data class UserMeetingEventListScreenState(
    val user: UserModel? = null,
    val events: List<MeetingEventModel> = listOf(),
)