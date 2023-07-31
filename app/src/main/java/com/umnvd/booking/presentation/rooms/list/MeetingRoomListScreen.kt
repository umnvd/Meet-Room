package com.umnvd.booking.presentation.rooms.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umnvd.booking.core.ui.components.AppFloatingActionButton
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenState
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenViewModel
import com.umnvd.booking.presentation.rooms.list.components.MeetingRoomListTile
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomListScreen(
    viewModel: MeetingRoomListScreenViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
    onRoomClick: (MeetingRoomModel) -> Unit,
    onCreateClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sync by syncViewModel.sync.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(sync) {
        if (sync) {
            viewModel.loadRooms()
            syncViewModel.syncHandled()
        }
    }

    MeetingRoomListScreenContent(
        state = state,
        onRoomClick = onRoomClick,
        onCreateClick = onCreateClick,
        onRetryCLick = viewModel::loadRooms,
    )
}

@Composable
private fun MeetingRoomListScreenContent(
    state: MeetingRoomListScreenState,
    onRoomClick: (MeetingRoomModel) -> Unit = {},
    onCreateClick: () -> Unit = {},
    onRetryCLick: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = onCreateClick,
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimatedVisibility(
                visible = state.error != null,
                enter = slideInVertically(),
                exit = slideOutVertically(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = state.error!!.text,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Retry")
                    }
                }
            }
            if (state.rooms.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(
                        items = state.rooms,
                        key = MeetingRoomModel::uid,
                    ) {
                        MeetingRoomListTile(room = it, onRoomClick = onRoomClick)
                        Divider(color = MaterialTheme.colorScheme.divider)
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "There are no rooms",
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }

    }
}


@Preview
@Composable
private fun MeetingRoomListScreenContentPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomListScreenContent(
            state = MeetingRoomListScreenState(
                rooms = PreviewMocks.MeetingRooms().roomList,
            )
        )
    }
}

@Preview
@Composable
private fun MeetingRoomListScreenContentEmptyListPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomListScreenContent(
            state = MeetingRoomListScreenState(
                rooms = listOf(),
            )
        )
    }
}

@Preview
@Composable
private fun MeetingRoomListScreenContentLoadingPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomListScreenContent(
            state = MeetingRoomListScreenState(
                loading = true,
                rooms = PreviewMocks.MeetingRooms().roomList,
            )
        )
    }
}

@Preview
@Composable
private fun MeetingRoomListScreenContentErrorPreview() {
    MeetingRoomBookingTheme {
        MeetingRoomListScreenContent(
            state = MeetingRoomListScreenState(
                error = NetworkException(),
                rooms = PreviewMocks.MeetingRooms().roomList,
            )
        )
    }
}