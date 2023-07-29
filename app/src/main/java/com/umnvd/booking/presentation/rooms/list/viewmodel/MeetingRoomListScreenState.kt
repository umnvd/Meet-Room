package com.umnvd.booking.presentation.rooms.list.viewmodel

import androidx.compose.runtime.Immutable
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel

@Immutable
data class MeetingRoomListScreenState(
    val rooms: List<MeetingRoomModel> = listOf(),
    val error: AppException? = null,
    val loading: Boolean = false,
)
