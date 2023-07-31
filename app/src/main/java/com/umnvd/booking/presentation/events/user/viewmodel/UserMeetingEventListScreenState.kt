package com.umnvd.booking.presentation.events.user.viewmodel

import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import javax.annotation.concurrent.Immutable

@Immutable
data class UserMeetingEventListScreenState(
    val events: List<MeetingEventModel> = listOf(),
    val loading: Boolean = false,
    val error: AppException? = null,
)