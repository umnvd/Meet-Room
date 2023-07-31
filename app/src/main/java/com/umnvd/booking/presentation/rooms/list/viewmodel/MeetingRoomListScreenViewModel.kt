package com.umnvd.booking.presentation.rooms.list.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomListScreenViewModel @Inject constructor(
    private val getMeetingRoomListUseCase: GetMeetingRoomListUseCase,
) : BaseViewModel<MeetingRoomListScreenState>(MeetingRoomListScreenState()) {

    init {
        loadRooms()
    }

    fun loadRooms() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result = getMeetingRoomListUseCase()) {
                is Result.Success ->
                    updateState { it.copy(rooms = result.value) }

                is Result.Error ->
                    updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}