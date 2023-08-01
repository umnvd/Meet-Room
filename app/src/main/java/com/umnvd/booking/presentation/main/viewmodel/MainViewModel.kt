package com.umnvd.booking.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetMeetingEventListUseCase
import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomListUseCase
import com.umnvd.booking.domain.users.usecases.GetCurrentUserUseCase
import com.umnvd.booking.domain.users.usecases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMeetingEventListUseCase: GetMeetingEventListUseCase,
    private val getMeetingRoomListUseCase: GetMeetingRoomListUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : BaseViewModel<MainState>(MainState()) {

    init {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            val userJob = launch { loadCurrentUser() }
            val eventsJob = launch { loadEvents() }
            val roomsJob = launch { loadRooms() }
            val usersJob = launch { loadUsers() }
            joinAll(userJob, eventsJob, roomsJob, usersJob)
            updateState { it.copy(loading = false) }
        }
    }

    fun updateEvents() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            Log.d("MAIN_VIEWMODEL", "updateEvents: ${state.value.loading}")
            loadEvents()
            updateState { it.copy(loading = false) }
        }
    }

    fun updateRoomsAndEvents() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            Log.d("MAIN_VIEWMODEL", "updateRoomsAndEvents: ${state.value.loading}")
            loadRooms()
            loadEvents()
            updateState { it.copy(loading = false) }
        }
    }

    private suspend fun loadEvents() {
        when (val result = getMeetingEventListUseCase()) {
            is Result.Success -> updateState { it.copy(events = result.value) }
            is Result.Error -> updateState { it.copy(error = result.error) }
        }
    }

    private suspend fun loadRooms() {
        when (val result = getMeetingRoomListUseCase()) {
            is Result.Success -> updateState { it.copy(rooms = result.value) }
            is Result.Error -> updateState { it.copy(error = result.error) }
        }
    }

    private suspend fun loadUsers() {
        when (val result = getUserListUseCase()) {
            is Result.Success -> updateState { it.copy(users = result.value) }
            is Result.Error -> updateState { it.copy(error = result.error) }
        }
    }

    private suspend fun loadCurrentUser() {
        when (val result = getCurrentUserUseCase()) {
            is Result.Success -> updateState { it.copy(currentUser = result.value) }
            is Result.Error -> updateState { it.copy(error = result.error) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}