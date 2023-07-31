package com.umnvd.booking.presentation.events.user.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetUserMeetingEventListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserMeetingEventListScreenViewModel @Inject constructor(
    val getUserMeetingEventListUseCase: GetUserMeetingEventListUseCase,
) : BaseViewModel<UserMeetingEventListScreenState>(UserMeetingEventListScreenState()) {

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            when (val result = getUserMeetingEventListUseCase()) {
                is Result.Success -> updateState { it.copy(events = result.value) }
                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}