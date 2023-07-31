package com.umnvd.booking.presentation.events.creation.viewmodel

import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomListUseCase
import com.umnvd.booking.domain.users.usecases.GetUserListUseCase
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.viewmodels.BaseMeetingEventFormViewModel
import javax.inject.Inject

class MeetingEventCreationScreenViewModel @Inject constructor(
    private val getMeetingRoomListUseCase: GetMeetingRoomListUseCase,
    private val getUserListUseCase: GetUserListUseCase,
) : BaseMeetingEventFormViewModel<MeetingEventCreationScreenState>(
    MeetingEventCreationScreenState()
) {

    override fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

    fun createEvent() {
        TODO("Not yet implemented")
    }
}