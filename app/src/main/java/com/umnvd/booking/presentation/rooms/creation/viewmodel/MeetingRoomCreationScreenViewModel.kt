package com.umnvd.booking.presentation.rooms.creation.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.rooms.usecases.CreateMeetingRoomUseCase
import com.umnvd.booking.domain.rooms.usecases.ValidateMeetingRoomFormUseCase
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState
import com.umnvd.booking.presentation.rooms.common.form.toDomain
import com.umnvd.booking.presentation.rooms.common.viewmodels.BaseMeetingRoomFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingRoomCreationScreenViewModel @Inject constructor(
    private val validateMeetingRoomFormUseCase: ValidateMeetingRoomFormUseCase,
    private val createMeetingRoomUseCase: CreateMeetingRoomUseCase,
) : BaseMeetingRoomFormViewModel<MeetingRoomCreationScreenState>(MeetingRoomCreationScreenState()) {

    override fun updateForm(builder: (MeetingRoomFormState) -> MeetingRoomFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

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

    fun errorHandled() = updateState { it.copy(error = null) }
}