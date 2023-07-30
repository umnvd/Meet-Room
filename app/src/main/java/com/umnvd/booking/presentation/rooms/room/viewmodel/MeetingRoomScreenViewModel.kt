package com.umnvd.booking.presentation.rooms.room.viewmodel

import ROOM_ROUTE_UID_KEY
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.rooms.usecases.EditMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.ValidateMeetingRoomFormUseCase
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormState
import com.umnvd.booking.presentation.rooms.room.models.toDomain
import com.umnvd.booking.presentation.rooms.room.models.toFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomScreenViewModel @Inject constructor(
    private val validateMeetingRoomFormUseCase: ValidateMeetingRoomFormUseCase,
    private val getMeetingRoomUseCase: GetMeetingRoomUseCase,
    private val editMeetingRoomUseCase: EditMeetingRoomUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MeetingRoomScreenState>(MeetingRoomScreenState()), MeetingRoomFormController {

    private val uidArg = savedStateHandle.get<String>(ROOM_ROUTE_UID_KEY)
        ?: throw IllegalStateException("Meeting room UID not specified")

    init {
        loadRoom()
    }

    override fun setName(value: String) =
        updateForm { it.copy(name = FieldState(value)) }

    override fun setAddress(value: String) =
        updateForm { it.copy(address = FieldState(value)) }

    private fun loadRoom() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            when (val result =
                getMeetingRoomUseCase(GetMeetingRoomUseCase.Params(uidArg))) {
                is Result.Success -> updateState {
                    it.copy(
                        formState = result.value.toFormState(),
                        room = result.value,
                    )
                }

                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun editRoom() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val validationResult = validateMeetingRoomFormUseCase(
                ValidateMeetingRoomFormUseCase.Params(form = state.value.formState.toDomain())
            )

            when (validationResult) {
                is Result.Error -> updateForm {
                    it.copy(
                        name = it.name.copy(error = validationResult.error.name),
                        address = it.address.copy(error = validationResult.error.address),
                    )
                }

                is Result.Success -> {
                    val result = editMeetingRoomUseCase(
                        EditMeetingRoomUseCase.Params(
                            uid = uidArg,
                            form = state.value.formState.toDomain(),
                        )
                    )
                    when (result) {
                        is Result.Success -> updateState { it.copy(saved = true) }
                        is Result.Error -> updateState { it.copy(error = result.error) }
                    }
                }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun savedHandled() = resetState()

    fun errorHandled() = updateState { it.copy(error = null) }

    private fun updateForm(builder: (MeetingRoomFormState) -> MeetingRoomFormState) =
        updateState { it.copy(formState = builder(it.formState)) }
}