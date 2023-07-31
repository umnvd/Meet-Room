package com.umnvd.booking.presentation.rooms.common.viewmodels

import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState

abstract class BaseMeetingRoomFormViewModel<T>(
    initialState: T
) : BaseViewModel<T>(initialState), MeetingRoomFormController {

    protected abstract fun updateForm(builder: (MeetingRoomFormState) -> MeetingRoomFormState)

    override fun setName(value: String) =
        updateForm { it.copy(name = FieldState(value)) }

    override fun setAddress(value: String) =
        updateForm { it.copy(address = FieldState(value)) }
}