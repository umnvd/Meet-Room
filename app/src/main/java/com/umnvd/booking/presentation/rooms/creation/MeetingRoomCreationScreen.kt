package com.umnvd.booking.presentation.rooms.creation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.utils.rememberWithKeyboardHiding
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.common.form.toFormState
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.presentation.rooms.common.components.MeetingRoomForm
import com.umnvd.booking.presentation.rooms.creation.viewmodel.MeetingRoomCreationScreenState
import com.umnvd.booking.presentation.rooms.creation.viewmodel.MeetingRoomCreationScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomCreationScreen(
    viewModel: MeetingRoomCreationScreenViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel,
    onCreated: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(state.created) {
        if (state.created) {
            syncViewModel.trigger()
            onCreated()
        }
    }

    MeetingRoomCreationScreenContent(
        state = state,
        formController = viewModel,
        onCreateClick = viewModel::createRoom,
        onBackClick = onBackClick,
    )
}

@Composable
private fun MeetingRoomCreationScreenContent(
    state: MeetingRoomCreationScreenState,
    formController: MeetingRoomFormController,
    onCreateClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val onSaveClickHandler = rememberWithKeyboardHiding(onCreateClick)

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            AppBackNavigationTopBar(
                onBackClick = onBackClick,
                actions = {
                    Button(onClick = onSaveClickHandler) {
                        Text(text = stringResource(R.string.room_create_buttom))
                    }
                }
            )
        }
    ) { innerPadding ->
        MeetingRoomForm(
            formState = state.formState,
            formController = formController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Preview
@Composable
private fun MeetingRoomCreationScreenContentPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomCreationScreenContent(
            state = MeetingRoomCreationScreenState(
                formState = PreviewMocks.MeetingRooms().room.toFormState(),
            ),
            formController = PreviewMocks.FormController().meetingRoom,
        )
    }
}