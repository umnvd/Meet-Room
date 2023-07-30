package com.umnvd.booking.presentation.rooms.creation.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.viewmodel.BaseViewModel
import com.umnvd.booking.domain.rooms.usecases.CreateMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.ValidateMeetingRoomFormUseCase
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormState
import com.umnvd.booking.presentation.rooms.room.models.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomCreationScreenViewModel @Inject constructor(
    private val validateMeetingRoomFormUseCase: ValidateMeetingRoomFormUseCase,
    private val createMeetingRoomUseCase: CreateMeetingRoomUseCase,
) : BaseViewModel<MeetingRoomCreationScreenState>(MeetingRoomCreationScreenState()),
    MeetingRoomFormController {

    override fun setName(value: String) =
        updateForm { it.copy(name = FieldState(value)) }

    override fun setAddress(value: String) =
        updateForm { it.copy(address = FieldState(value)) }

    fun createRoom() {
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
                    val result = createMeetingRoomUseCase(
                        CreateMeetingRoomUseCase.Params(
                            form = state.value.formState.toDomain(),
                        )
                    )
                    when (result) {
                        is Result.Success -> updateState { it.copy(created = true) }
                        is Result.Error -> updateState { it.copy(error = result.error) }
                    }
                }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun createdHandled() = resetState()

    fun errorHandled() = updateState { it.copy(error = null) }

    private fun updateForm(builder: (MeetingRoomFormState) -> MeetingRoomFormState) =
        updateState { it.copy(formState = builder(it.formState)) }
}