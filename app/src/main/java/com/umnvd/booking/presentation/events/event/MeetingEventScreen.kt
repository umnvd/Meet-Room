package com.umnvd.booking.presentation.events.event

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.utils.rememberWithKeyboardHiding
import com.umnvd.booking.presentation.events.common.form.MeetingEventFormController
import com.umnvd.booking.presentation.events.common.widgets.form.MeetingEventForm
import com.umnvd.booking.presentation.events.creation.viewmodel.MeetingEventCreationScreenState
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenState
import com.umnvd.booking.presentation.events.event.viewmodel.MeetingEventScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingEventScreen(
    viewModel: MeetingEventScreenViewModel = hiltViewModel(),
    onSaved: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)

    LaunchedEffect(state.saved) {
        if (state.saved) {
            onSaved()
        }
    }

    MeetingEventScreenContent(
        state = state,
        formController = viewModel,
        onCreateClick = viewModel::saveEvent,
        onBackClick = onBackClick,
    )
}

@Composable
private fun MeetingEventScreenContent(
    state: MeetingEventScreenState,
    formController: MeetingEventFormController,
    onCreateClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val onSaveClickHandler = rememberWithKeyboardHiding(onCreateClick)

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
        MeetingEventForm(
            formState = state.formState,
            formController = formController,
            modifier = Modifier.padding(innerPadding)
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