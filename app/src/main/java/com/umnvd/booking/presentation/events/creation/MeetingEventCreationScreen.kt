package com.umnvd.booking.presentation.events.creation

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
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.components.MeetingEventForm
import com.umnvd.booking.presentation.events.creation.viewmodel.MeetingEventCreationScreenState
import com.umnvd.booking.presentation.events.creation.viewmodel.MeetingEventCreationScreenViewModel
import com.umnvd.booking.presentation.main.viewmodel.MainViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingEventCreationScreen(
    viewModel: MeetingEventCreationScreenViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    onCreated: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mainState by mainViewModel.state.collectAsStateWithLifecycle()

    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)
    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppProgressIndicatorController.current.state(mainState.loading)

    LaunchedEffect(
        key1 = mainState.events,
        key2 = mainState.rooms,
        key3 = mainState.users,
    ) {
        viewModel.setAllEvents(mainState.events)
        viewModel.setAllRooms(mainState.rooms)
        viewModel.setAllUsers(mainState.users)
    }

    LaunchedEffect(state.created) {
        if (state.created) {
            mainViewModel.updateEvents()
            onCreated()
        }
    }

    MeetingEventCreationScreenContent(
        state = state,
        formController = viewModel,
        onCreateClick = viewModel::createEvent,
        onBackClick = onBackClick,
    )
}

@Composable
private fun MeetingEventCreationScreenContent(
    state: MeetingEventCreationScreenState,
    formController: MeetingEventFormController,
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
                        Text(text = stringResource(R.string.event_create_button))
                    }
                }
            )
        }
    ) { innerPadding ->
        MeetingEventForm(
            formState = state.formState,
            formController = formController,
            allRooms = state.allRooms,
            allUsers = state.allUsers,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview
@Composable
private fun MeetingEventCreationScreenContentPreview() {
    MeetingRoomBookingTheme {
        MeetingEventCreationScreenContent(
            state = MeetingEventCreationScreenState(),
            formController = PreviewMocks.FormController().meetingEvent,
        )
    }
}