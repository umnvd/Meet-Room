package com.umnvd.booking.presentation.rooms.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.domain.NetworkException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.presentation.rooms.list.components.MeetingRoomListTile
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenState
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenViewModel
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
    )
}

@Composable
private fun MeetingRoomListScreenContent(
    state: MeetingRoomListScreenState,
    onRoomClick: (MeetingRoomModel) -> Unit = {},
    onCreateClick: () -> Unit = {},
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(
                items = state.rooms,
                key = MeetingRoomModel::uid,
            ) {
                MeetingRoomListTile(room = it, onRoomClick = onRoomClick)
                Divider(color = MaterialTheme.colorScheme.divider)
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