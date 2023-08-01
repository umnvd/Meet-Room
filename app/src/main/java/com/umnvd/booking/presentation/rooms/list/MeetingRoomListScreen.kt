package com.umnvd.booking.presentation.rooms.list

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
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
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.presentation.main.viewmodel.MainViewModel
import com.umnvd.booking.presentation.rooms.list.components.MeetingRoomListTile
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenState
import com.umnvd.booking.presentation.rooms.list.viewmodel.MeetingRoomListScreenViewModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomListScreen(
    viewModel: MeetingRoomListScreenViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    onRoomClick: (MeetingRoomModel) -> Unit,
    onCreateClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mainState by mainViewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(mainState.loading)

    LaunchedEffect(mainState.rooms) { viewModel.setRooms(mainState.rooms) }

    MeetingRoomListScreenContent(
        state = state,
        onRoomClick = onRoomClick,
        onCreateClick = onCreateClick,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MeetingRoomListScreenContent(
    state: MeetingRoomListScreenState,
    onRoomClick: (MeetingRoomModel) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = onCreateClick,
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
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