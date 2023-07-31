package com.umnvd.booking.presentation.events.common.viewmodels

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodels.BaseViewModel
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import java.time.LocalDate
import java.time.LocalTime

abstract class BaseMeetingEventFormViewModel<T>(
    initialState: T,
) : BaseViewModel<T>(initialState), MeetingEventFormController {

    abstract fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState)

    override fun setTitle(value: String) =
        updateForm { it.copy(title = FieldState(value)) }

    override fun setDescription(value: String) =
        updateForm { it.copy(description = FieldState(value)) }

    override fun setStartDate(value: LocalDate) =
        updateForm {
            if (value != it.startDate.value)
                it.copy(
                    startDate = FieldState(value),
                    startTime = FieldState(it.startTime.value),
                    endDate = FieldState(it.endDate.value),
                    endTime = FieldState(it.endTime.value),
                )
            else it
        }

    override fun setStartTime(value: LocalTime) =
        updateForm {
            if (value != it.startTime.value)
                it.copy(
                    startTime = FieldState(value),
                    startDate = FieldState(it.startDate.value),
                    endDate = FieldState(it.endDate.value),
                    endTime = FieldState(it.endTime.value),
                )
            else it
        }

    override fun setEndDate(value: LocalDate) =
        updateForm {
            if (value != it.endDate.value)
                it.copy(
                    endDate = FieldState(value),
                    startDate = FieldState(it.startDate.value),
                    startTime = FieldState(it.startTime.value),
                    endTime = FieldState(it.endTime.value),
                )
            else it
        }

    override fun setEndTime(value: LocalTime) =
        updateForm {
            if (value != it.endTime.value)
                it.copy(
                    endTime = FieldState(value),
                    startDate = FieldState(it.startDate.value),
                    startTime = FieldState(it.startTime.value),
                    endDate = FieldState(it.endDate.value),
                )
            else it
        }

    override fun setRoom(value: MeetingRoomModel) =
        updateForm { it.copy(room = FieldState(value)) }

    override fun addUser(item: UserModel) =
        updateForm { it.copy(participants = FieldState(it.participants.value + item)) }

    override fun removeUser(item: UserModel) =
        updateForm { it.copy(participants = FieldState(it.participants.value - item)) }
}