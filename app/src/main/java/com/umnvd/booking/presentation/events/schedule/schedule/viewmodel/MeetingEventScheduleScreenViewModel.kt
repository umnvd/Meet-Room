package com.umnvd.booking.presentation.events.schedule.schedule.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.navigation.navigations.EVENT_SCHEDULE_GRAPH_DATE_KEY
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.events.usecases.GetDayMeetingEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MeetingEventScheduleScreenViewModel @Inject constructor(
    private val getDayMeetingEventsUseCase: GetDayMeetingEventsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MeetingEventScheduleScreenState>(MeetingEventScheduleScreenState()) {

    private val dateArg = savedStateHandle.get<String>(EVENT_SCHEDULE_GRAPH_DATE_KEY)
    private var loadEventsJob: Job? = null

    init {
        updateState {
            it.copy(date = dateArg?.let(LocalDate::parse) ?: LocalDate.now())
        }
        loadEvents()
    }

    fun setDate(value: LocalDate) {
        updateState { it.copy(date = value) }
        loadEvents()
    }

    private fun loadEvents() {
        if (loadEventsJob != null) {
            loadEventsJob?.cancel()
            updateState { it.copy(loading = false) }
        }

        viewModelScope.launch {
            updateState {
                it.copy(
                    loading = true,
                    events = listOf(),
                )
            }
            loadEventsJob = launch {
                val result = getDayMeetingEventsUseCase(
                    GetDayMeetingEventsUseCase.Params(date = state.value.date)
                )
                when (result) {
                    is Result.Success -> updateState { it.copy(events = result.value) }
                    is Result.Error -> updateState { it.copy(error = result.error) }
                }
            }
            loadEventsJob?.join()
            updateState { it.copy(loading = false) }
        }
    }
}