package com.umnvd.booking.presentation.events.event.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.core.navigation.navigations.EVENT_ROUTE_UID_KEY
import com.umnvd.booking.domain.events.usecases.DeleteMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.EditMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.GetMeetingEventUseCase
import com.umnvd.booking.domain.events.usecases.GetUsersAndMeetingRoomsUseCase
import com.umnvd.booking.domain.events.usecases.ValidateMeetingEventUseCase
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.presentation.events.common.form.toDomain
import com.umnvd.booking.presentation.events.common.form.toFormState
import com.umnvd.booking.presentation.events.common.viewmodels.BaseMeetingEventFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingEventScreenViewModel @Inject constructor(
    private val getUsersAndMeetingRoomsUseCase: GetUsersAndMeetingRoomsUseCase,
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

    fun saveEvent() {
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val validationResult = validateMeetingEventUseCase(
                ValidateMeetingEventUseCase.Params(state.value.formState.toDomain())
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
            when (val usersWithRoomsResult =
                getUsersAndMeetingRoomsUseCase()) {
                is Result.Success -> updateState {
                    it.copy(
                        allRooms = usersWithRoomsResult.value.rooms,
                        allUsers = usersWithRoomsResult.value.users
                    )
                }

                is Result.Error -> updateState { it.copy(error = usersWithRoomsResult.error) }
            }

            val eventResult = getMeetingEventUseCase(
                GetMeetingEventUseCase.Params(uidArg)
            )
            when (eventResult) {
                is Result.Success -> updateState {
                    it.copy(
                        event = eventResult.value,
                        formState = eventResult.value.toFormState()
                    )
                }

                is Result.Error -> updateState { it.copy(error = eventResult.error) }
            }
            updateState { it.copy(loading = false) }
        }
    }
}