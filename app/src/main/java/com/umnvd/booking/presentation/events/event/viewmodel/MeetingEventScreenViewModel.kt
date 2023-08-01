package com.umnvd.booking.presentation.events.event.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.navigation.navigations.EVENT_ROUTE_UID_KEY
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.usecases.DeleteMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.EditMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.GetMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.ValidateMeetingEventUseCase
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.form.toDomain
import com.umnvd.booking.presentation.events.common.form.toFormState
import com.umnvd.booking.presentation.events.common.viewmodels.BaseMeetingEventFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingEventScreenViewModel @Inject constructor(
    private val getMeetingEventUseCase: GetMeetingEventUseCase,
    private val editMeetingEventUseCase: EditMeetingEventUseCase,
    private val deleteMeetingEventUseCase: DeleteMeetingEventUseCase,
    private val validateMeetingEventUseCase: ValidateMeetingEventUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseMeetingEventFormViewModel<MeetingEventScreenState>(
    MeetingEventScreenState(),
) {

    private val uidArg = savedStateHandle.get<String>(EVENT_ROUTE_UID_KEY)
        ?: throw IllegalStateException("Meeting room UID not specified")

    init {
        loadData()
    }

    override fun updateForm(builder: (MeetingEventFormState) -> MeetingEventFormState) =
        updateState { it.copy(formState = builder(it.formState)) }

    fun setAllEvents(value: List<MeetingEventModel>) = updateState { it.copy(allEvents = value) }
    fun setAllRooms(value: List<MeetingRoomModel>) = updateState { it.copy(allRooms = value) }
    fun setAllUsers(value: List<UserModel>) = updateState { it.copy(allUsers = value) }

    fun saveEvent() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val validationResult = validateMeetingEventUseCase(
                ValidateMeetingEventUseCase.Params(
                    form = state.value.formState.toDomain(),
                    uid = uidArg,
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
                    val result = editMeetingEventUseCase(
                        EditMeetingEventUseCase.Params(
                            uid = uidArg,
                            form = state.value.formState.toDomain(),
                        )
                    )
                    when (result) {
                        is Result.Error -> updateState { it.copy(error = result.error) }
                        is Result.Success -> updateState { it.copy(saved = true) }
                    }
                }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun deleteEvent() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val result = deleteMeetingEventUseCase(
                DeleteMeetingEventUseCase.Params(uidArg)
            )
            when (result) {
                is Result.Success -> updateState { it.copy(deleted = true) }
                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }

    fun errorHandled() = updateState { it.copy(error = null) }

    private fun loadData() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val result = getMeetingEventUseCase(
                GetMeetingEventUseCase.Params(uidArg)
            )
            when (result) {
                is Result.Success -> updateState {
                    it.copy(
                        event = result.value,
                        formState = result.value.toFormState()
                    )
                }

                is Result.Error -> updateState { it.copy(error = result.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }
}