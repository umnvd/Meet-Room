package com.umnvd.booking.presentation.events.event

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.utils.rememberWithKeyboardHiding
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.components.form.MeetingEventForm
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenState
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingEventScreen(
    viewModel: MeetingEventScreenViewModel = hiltViewModel(),
    onSaved: () -> Unit,
    onDeleted: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewmodel::errorHandled)

    LaunchedEffect(state.saved) { if (state.saved) onSaved() }
    LaunchedEffect(state.deleted) { if (state.deleted) onDeleted() }

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

    if (deleteDialogShowing) {
        AlertDialog(
            onDismissRequest = { deleteDialogShowing = false },
            title = { Text("Are you sure you want to delete event?") },
            text = { Text("This action cannot be undone") },
            confirmButton = {
                TextButton(onClick = onDeleteClick) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteDialogShowing = false }) {
                    Text("Cancel")
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
                        label = "event_actions_anim",
                        ) {
                        Row {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Delete")
                            }

                            if (it) {
                                Button(onClick = onSaveClickHandler) {
                                    Text(text = "Save")
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