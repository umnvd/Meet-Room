package com.umnvd.booking.presentation.events.common.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormState
import com.umnvd.booking.util.PreviewMocks


@Composable
fun MeetingEventForm(
    formState: MeetingEventFormState,
    allRooms: List<MeetingRoomModel>,
    allUsers: List<UserModel>,
    formController: MeetingEventFormController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MainInfoForm(
            title = formState.title,
            description = formState.description,
            setTitle = formController::setTitle,
            setDescription = formController::setDescription,
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        DurationForm(
            startDate = formState.startDate,
            startTime = formState.startTime,
            endDate = formState.endDate,
            endTime = formState.endTime,
            onStartDateChanged = formController::setStartDate,
            onStartTimeChanged = formController::setStartTime,
            onEndDateChanged = formController::setEndDate,
            onEndTimeChanged = formController::setEndTime,
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        RoomForm(
            room = formState.room,
            rooms = allRooms, // TODO
            onRoomSelected = formController::setRoom,
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        ParticipantsForm(
            participants = formState.participants,
            users = allUsers, // TODO
            onParticipantSelected = formController::addUser,
            onParticipantRemoved = formController::removeUser,
        )
    }
}


@Preview(locale = "ru")
@Composable
private fun MeetingEventFormPreview() {
    MeetingRoomBookingTheme {
        Surface {
            MeetingEventForm(
                formState = MeetingEventFormState(),
                formController = PreviewMocks.FormController().meetingEvent,
                allRooms = PreviewMocks.MeetingRooms().roomList,
                allUsers = PreviewMocks.Users().userList,
            )
        }
    }
}

@Preview
@Composable
private fun MeetingEventFormPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface {
            MeetingEventForm(
                formState = MeetingEventFormState(),
                formController = PreviewMocks.FormController().meetingEvent,
                allRooms = PreviewMocks.MeetingRooms().roomList,
                allUsers = PreviewMocks.Users().userList,
            )
        }
    }
}