package com.umnvd.booking.presentation.rooms.room

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.utils.rememberWithKeyboardHiding
import com.umnvd.booking.presentation.rooms.common.form.MeetingRoomFormController
import com.umnvd.booking.presentation.rooms.common.form.toFormState
import com.umnvd.booking.presentation.rooms.common.viewmodels.MeetingRoomSyncViewModel
import com.umnvd.booking.presentation.rooms.common.components.MeetingRoomForm
import com.umnvd.booking.presentation.rooms.room.viewmodel.MeetingRoomScreenState
import com.umnvd.booking.presentation.rooms.room.viewmodel.MeetingRoomScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomScreen(
    viewModel: MeetingRoomScreenViewModel = hiltViewModel(),
    syncViewModel: MeetingRoomSyncViewModel,
    onSaved: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(state.saved) {
        if (state.saved) {
            syncViewModel.triggerSync()
            onSaved()
        }
    }

    MeetingRoomScreenContent(
        state = state,
        formController = viewModel,
        onSaveClick = viewModel::saveRoom,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MeetingRoomScreenContent(
    state: MeetingRoomScreenState,
    formController: MeetingRoomFormController,
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val onSaveClickHandler = rememberWithKeyboardHiding(onSaveClick)

    Scaffold(
        topBar = {
            AppBackNavigationTopBar(
                onBackClick = onBackClick,
                actions = {
                    AnimatedVisibility(
                        visible = state.saveButtonVisible,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Button(onClick = onSaveClickHandler) {
                            Text(text = "Save")
                        }
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
private fun MeetingRoomScreenContentPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomScreenContent(
            state = MeetingRoomScreenState(
                formState = PreviewMocks.MeetingRooms().room.toFormState(),
                room = PreviewMocks.MeetingRooms().room,
            ),
            formController = PreviewMocks.FormController().meetingRoom,
        )
    }
}