package com.umnvd.booking.presentation.events.user.viewmodel

import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.users.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserMeetingEventListScreenViewModel @Inject constructor() :
    BaseViewModel<UserMeetingEventListScreenState>(UserMeetingEventListScreenState()) {

    fun setUser(value: UserModel?) = updateState { it.copy(user = value) }
    fun onUpdateEvents(value: List<MeetingEventModel>) = updateState {
        it.copy(events = value.filter { event -> event.participants.contains(it.user) })
    }
}