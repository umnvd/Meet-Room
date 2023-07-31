package com.umnvd.booking.presentation.rooms.room.viewmodel

import com.umnvd.booking.core.navigation.navigations.ROOM_ROUTE_UID_KEY
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.rooms.usecases.EditMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.GetMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.ValidateMeetingRoomFormUseCase
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState
import com.umnvd.booking.presentation.rooms.common.form.toDomain
import com.umnvd.booking.presentation.rooms.common.form.toFormState
import com.umnvd.booking.presentation.rooms.common.viewmodels.BaseMeetingRoomFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomScreenViewModel @Inject constructor(
    private val validateMeetingRoomFormUseCase: ValidateMeetingRoomFormUseCase,
    private val getMeetingRoomUseCase: GetMeetingRoomUseCase,
    private val editMeetingRoomUseCase: EditMeetingRoomUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseMeetingRoomFormViewModel<MeetingRoomScreenState>(MeetingRoomScreenState()) {

    private val uidArg = savedStateHandle.get<String>(ROOM_ROUTE_UID_KEY)
        ?: throw IllegalStateException("Meeting room UID not specified")

    init {
        loadRoom()
    }

    override fun updateForm(builder: (MeetingRoomFormState) -> MeetingRoomFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

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

    fun saveRoom() {
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

    fun errorHandled() = updateState { it.copy(error = null) }
}