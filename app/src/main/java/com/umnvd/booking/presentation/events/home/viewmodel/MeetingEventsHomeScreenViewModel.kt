package com.umnvd.booking.presentation.events.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetMeetingEventListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MeetingEventsHomeScreenViewModel @Inject constructor(
    private val getMeetingEventListUseCase: GetMeetingEventListUseCase,
) : BaseViewModel<MeetingEventsHomeScreenState>(MeetingEventsHomeScreenState()) {

    init {
        loadEvents()
    }

    fun setDate(value: LocalDate) = updateState { it.copy(date = value) }

    fun setToday() = updateState { it.copy(date = LocalDate.now()) }

    fun loadEvents() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            when (val result = getMeetingEventListUseCase()) {
                is Result.Success -> updateState { it.copy(events = result.value) }
                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }

}