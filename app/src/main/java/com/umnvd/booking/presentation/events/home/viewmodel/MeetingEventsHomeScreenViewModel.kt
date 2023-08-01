package com.umnvd.booking.presentation.events.home.viewmodel

import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MeetingEventsHomeScreenViewModel @Inject constructor()
    : BaseViewModel<MeetingEventsHomeScreenState>(MeetingEventsHomeScreenState()) {

    fun setEvents(value: List<MeetingEventModel>) = updateState { it.copy(events = value) }

    fun setDate(value: LocalDate) = updateState { it.copy(date = value) }

    fun setToday() = updateState { it.copy(date = LocalDate.now()) }
}