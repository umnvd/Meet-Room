package com.umnvd.booking.presentation.rooms.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormState
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomForm(
    formState: MeetingRoomFormState,
    formController: MeetingRoomFormController,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTextField(
            value = formState.name.value,
            error = formState.name.error?.text,
            onValueChange = formController::setName,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 28.sp,
            ),
            modifier = Modifier.padding(start = 40.dp),
            placeholder = stringResource(R.string.room_name_hint),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = stringResource(R.string.address_icon_description),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            AppTextField(
                value = formState.address.value,
                error = formState.address.error?.text,
                onValueChange = formController::setAddress,
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(R.string.room_address_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
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