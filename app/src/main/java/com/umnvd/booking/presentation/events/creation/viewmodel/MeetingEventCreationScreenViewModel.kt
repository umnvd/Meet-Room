package com.umnvd.booking.presentation.events.creation.viewmodel

import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.usecases.CreateMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.ValidateMeetingEventUseCase
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.form.toDomain
import com.umnvd.booking.presentation.events.common.viewmodels.BaseMeetingEventFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingEventCreationScreenViewModel @Inject constructor(
    private val createMeetingEventUseCase: CreateMeetingEventUseCase,
    private val validateMeetingEventUseCase: ValidateMeetingEventUseCase,
) : BaseMeetingEventFormViewModel<MeetingEventCreationScreenState>(
    MeetingEventCreationScreenState()
) {
    override fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

    fun setAllEvents(value: List<MeetingEventModel>) = updateState { it.copy(allEvents = value) }
    fun setAllRooms(value: List<MeetingRoomModel>) = updateState { it.copy(allRooms = value) }
    fun setAllUsers(value: List<UserModel>) = updateState { it.copy(allUsers = value) }

    fun createEvent() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val validationResult = validateMeetingEventUseCase(
                ValidateMeetingEventUseCase.Params(
                    form = state.value.formState.toDomain(),
                    events = state.value.allEvents,
                )
            )
            when (validationResult) {
                is Result.Error -> {
                    updateForm {
                        it.copy(
                            title = it.title.copy(error = validationResult.error.title),
                            startDate = it.startDate.copy(error = validationResult.error.startAt),
                            endDate = it.endDate.copy(error = validationResult.error.endAt),
                            room = it.room.copy(error = validationResult.error.room),
                            participants = it.participants
                                .copy(error = validationResult.error.participants),
                        )
                    }
                }

                is Result.Success -> {
                    val result = createMeetingEventUseCase(
                        CreateMeetingEventUseCase.Params(state.value.formState.toDomain())
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