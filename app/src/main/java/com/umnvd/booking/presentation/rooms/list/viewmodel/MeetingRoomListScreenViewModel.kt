package com.umnvd.booking.presentation.rooms.list.viewmodel

import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeetingRoomListScreenViewModel @Inject constructor() :
    BaseViewModel<MeetingRoomListScreenState>(MeetingRoomListScreenState()) {

    fun setRooms(value: List<MeetingRoomModel>) = updateState { it.copy(rooms = value) }
}