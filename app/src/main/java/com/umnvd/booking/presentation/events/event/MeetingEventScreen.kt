package com.umnvd.booking.presentation.events.event

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.components.MeetingEventForm
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenState
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingEventScreen(
    viewModel: MeetingEventScreenViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel,
    onSaved: () -> Unit,
    onDeleted: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(state.saved) {
        if (state.saved) {
            syncViewModel.trigger()
            onSaved()
        }
    }
    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            syncViewModel.trigger()
            onDeleted()
        }
    }

    MeetingEventScreenContent(
        state = state,
        formController = viewModel,
        onCreateClick = viewModel::saveEvent,
        onDeleteClick = viewModel::deleteEvent,
        onBackClick = onBackClick,
    )
}

@Composable
private fun MeetingEventScreenContent(
    state: MeetingEventScreenState,
    formController: MeetingEventFormController,
    onCreateClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val onSaveClickHandler = rememberWithKeyboardHiding(onCreateClick)
    var deleteDialogShowing by remember { mutableStateOf(false) }
    val onDeleteClickHandler = rememberWithKeyboardHiding {
        deleteDialogShowing = true
    }

    if (deleteDialogShowing) {
        AlertDialog(
            onDismissRequest = { deleteDialogShowing = false },
            title = { Text(text = stringResource(R.string.event_delete_dialog_title)) },
            text = { Text(text = stringResource(R.string.event_delete_dialog_message)) },
            confirmButton = {
                TextButton(onClick = {
                    deleteDialogShowing = false
                    onDeleteClick()
                }) {
                    Text(text = stringResource(R.string.event_delete_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteDialogShowing = false }) {
                    Text(text = stringResource(R.string.event_cancel_button))
                }
            },
        )
    }

    Scaffold(
        topBar = {
            AppBackNavigationTopBar(
                onBackClick = onBackClick,
                actions = {
                    Crossfade(
                        targetState = state.saveButtonVisible,
                        label = "meeting_event_actions_anim",
                    ) {
                        Row {
                            TextButton(onClick = onDeleteClickHandler) {
                                Text(text = stringResource(R.string.event_delete_button))
                            }
                            if (it) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = onSaveClickHandler,
                                    modifier = Modifier.padding(end = 8.dp),
                                ) {
                                    Text(text = stringResource(R.string.event_save_button))
                                }
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        MeetingEventForm(
            modifier = Modifier.padding(innerPadding),
            formState = state.formState,
            formController = formController,
            allRooms = state.allRooms,
            allUsers = state.allUsers,
        )
    }
}

@Preview
@Composable
private fun MeetingEventScreenContentPreview() {
    MeetingRoomBookingTheme {
        MeetingEventScreenContent(
            state = MeetingEventScreenState(),
            formController = PreviewMocks.FormController().meetingEvent,
        )
    }
}