package com.umnvd.booking.presentation.rooms.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomListScreenViewModel @Inject constructor(
    private val getMeetingRoomsUseCase: GetMeetingRoomsUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow(MeetingRoomListScreenState())
    val state: StateFlow<MeetingRoomListScreenState> = _state

    init {
        loadRooms()
    }

    fun loadRooms() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result = getMeetingRoomsUseCase()) {
                is Result.Success ->
                    _state.update { it.copy(rooms = result.value) }

                is Result.Error ->
                    _state.update { it.copy(error = result.error) }
            }
            _state.update { it.copy(loading = false) }
        }
    }
}