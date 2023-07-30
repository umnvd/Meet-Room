package com.umnvd.booking.presentation.events.schedule.calendar.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetMeetingEventDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingEventCalendarScreenViewModel @Inject constructor(
    private val getMeetingEventDaysUseCase: GetMeetingEventDaysUseCase,
) : BaseViewModel<MeetingEventCalendarScreenState>(MeetingEventCalendarScreenState()) {

    init {
        loadEventDays()
    }

    private fun loadEventDays() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            when (val result = getMeetingEventDaysUseCase()) {
                is Result.Success -> updateState { it.copy(eventDays = result.value) }
                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }
}