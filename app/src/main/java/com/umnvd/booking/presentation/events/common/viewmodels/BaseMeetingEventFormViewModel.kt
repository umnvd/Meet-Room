package com.umnvd.booking.presentation.events.common.viewmodels

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import java.time.LocalDate
import java.time.LocalTime

abstract class BaseMeetingEventFormViewModel<T>(
    initialState: T
) : BaseViewModel<T>(initialState), MeetingEventFormController {

    abstract fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState)

    override fun setTitle(value: String) =
        updateForm { it.copy(title = FieldState(value)) }

    override fun setDescription(value: String) =
        updateForm { it.copy(description = FieldState(value)) }

    override fun setStartDate(value: LocalDate) =
        updateForm { it.copy(startDate = FieldState(value)) }

    override fun setStartTime(value: LocalTime) =
        updateForm { it.copy(startTime = FieldState(value)) }

    override fun setEndDate(value: LocalDate) =
        updateForm { it.copy(endDate = FieldState(value)) }

    override fun setEndTime(value: LocalTime) =
        updateForm { it.copy(endTime = FieldState(value)) }

    override fun setRoom(value: MeetingRoomModel) =
        updateForm { it.copy(room = FieldState(value)) }

    override fun addUser(item: UserModel) =
        updateForm { it.copy(participants = FieldState(it.participants.value + item)) }

    override fun removeUser(item: UserModel) =
        updateForm { it.copy(participants = FieldState(it.participants.value - item)) }
}