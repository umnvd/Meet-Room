package com.umnvd.booking.presentation.rooms.creation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.presentation.rooms.common.viewmodels.MeetingRoomSyncViewModel
import com.umnvd.booking.presentation.rooms.common.widgets.MeetingRoomForm
import com.umnvd.booking.presentation.rooms.creation.viewmodel.MeetingRoomCreationScreenState
import com.umnvd.booking.presentation.rooms.creation.viewmodel.MeetingRoomCreationScreenViewModel
import com.umnvd.booking.presentation.rooms.room.models.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.room.models.toFormState
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomCreationScreen(
    viewModel: MeetingRoomCreationScreenViewModel = hiltViewModel(),
    syncViewModel: MeetingRoomSyncViewModel,
    onCreated: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)

    LaunchedEffect(state.created) {
        if (state.created) {
            syncViewModel.triggerSync()
            onCreated()
            viewModel.createdHandled()
        }
    }

    MeetingRoomCreationScreenContent(
        state = state,
        formController = viewModel,
        onCreateClick = viewModel::createRoom,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MeetingRoomCreationScreenContent(
    state: MeetingRoomCreationScreenState,
    formController: MeetingRoomFormController,
    onCreateClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val onSaveClickHandler = remember {
        fun() {
            onCreateClick()
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    Scaffold(
        topBar = {
            AppBackNavigationTopBar(
                onBackClick = onBackClick,
                actions = {
                    Button(onClick = onSaveClickHandler) {
                        Text(text = "Create")
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