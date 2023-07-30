package com.umnvd.booking.presentation.rooms.common.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormState
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomForm(
    formState: MeetingRoomFormState,
    formController: MeetingRoomFormController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        AppTextField(
            value = formState.name.value,
            error = formState.name.error?.text,
            onValueChange = formController::setName,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 28.sp,
            ),
            modifier = Modifier.padding(start = 40.dp),
            placeholder = "Name",
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            AppTextField(
                value = formState.address.value,
                error = formState.address.error?.text,
                onValueChange = formController::setAddress,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Address",
            )
        }
    }
}


@Preview
@Composable
private fun RoomFormViewPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            MeetingRoomForm(
                formState = MeetingRoomFormState(),
                formController = PreviewMocks.FormController().meetingRoom
            )
        }
    }
}

@Preview
@Composable
private fun RoomFormViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            MeetingRoomForm(
                formState = MeetingRoomFormState(),
                formController = PreviewMocks.FormController().meetingRoom
            )
        }
    }
}