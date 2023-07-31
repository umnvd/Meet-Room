package com.umnvd.booking.presentation.events.event.viewmodel

import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomListUseCase
import com.umnvd.booking.domain.users.usecases.GetUserListUseCase
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.viewmodels.BaseMeetingEventFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeetingEventScreenViewModel @Inject constructor(
    private val getMeetingRoomListUseCase: GetMeetingRoomListUseCase,
    private val getUserListUseCase: GetUserListUseCase,
) : BaseMeetingEventFormViewModel<MeetingEventScreenState>(MeetingEventScreenState()) {

    override fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

    fun saveEvent() {
        TODO("Not yet implemented")
    }
}