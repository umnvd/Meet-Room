package com.umnvd.booking.presentation.events.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppBackNavigationTopBar
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.home.schedule.components.EventScheduleTile
import com.umnvd.booking.presentation.events.user.viewmodel.UserMeetingEventListScreenState
import com.umnvd.booking.presentation.events.user.viewmodel.UserMeetingEventListScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun UserMeetingEventListScreen(
    viewModel: UserMeetingEventListScreenViewModel = hiltViewModel(),
    onEventClick: (MeetingEventModel) -> Unit,
    onBackCLick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    UserMeetingEventListScreenContent(
        state = state,
        onEventClick = onEventClick,
        onBackCLick = onBackCLick,
    )
}

@Composable
private fun UserMeetingEventListScreenContent(
    state: UserMeetingEventListScreenState,
    onEventClick: (MeetingEventModel) -> Unit = {},
    onBackCLick: () -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            AppBackNavigationTopBar(onBackClick = onBackCLick)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(items = state.events, key = { it.uid }) {
                EventScheduleTile(
                    event = it,
                    onEventClick = onEventClick,
                    modifier = Modifier
                        .height(64.dp)
                        .padding(horizontal = 16.dp)
                )
                Divider(
                    color = MaterialTheme.colorScheme.divider,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}


@Preview
@Composable
private fun UserMeetingEventListScreenContentPreview() {
    MeetingRoomBookingTheme {
        UserMeetingEventListScreenContent(
            state = UserMeetingEventListScreenState(
                events = PreviewMocks.MeetingEvents().eventList
            ),
        )
    }
}