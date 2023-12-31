package com.umnvd.booking.presentation.events.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppErrorText
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.domain.RoomNameRequiredException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.util.PreviewMocks
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RoomForm(
    room: FieldState<MeetingRoomModel?>,
    rooms: List<MeetingRoomModel>,
    onRoomSelected: (MeetingRoomModel) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    if (bottomSheetState.isVisible) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                coroutineScope.launch { bottomSheetState.hide() }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 32.dp)) {
                items(items = rooms, key = { it.uid }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onRoomSelected(it)
                                coroutineScope.launch { bottomSheetState.hide() }
                            }
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier.weight(1f),
                        )
                        if (it == room.value) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = stringResource(
                                    R.string.event_room_selected_icon_description
                                ),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.divider)
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = Icons.Outlined.MeetingRoom,
            contentDescription = stringResource(R.string.room_icon_description),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = room.value?.name ?: stringResource(R.string.event_select_room_hint),
                color = if (room.value == null)
                    MaterialTheme.colorScheme.hint
                else
                    MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { coroutineScope.launch { bottomSheetState.show() } }
                    .padding(horizontal = 12.dp, vertical = 16.dp),
            )
            if (room.error != null) {
                AppErrorText(
                    text = room.error.text,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 2.dp
                    ),
                )
            }
        }
    }
}


@Preview
@Composable
private fun RoomFormPreview() {
    MeetingRoomBookingTheme {
        Surface {
            RoomForm(
                room = FieldState(PreviewMocks.MeetingRooms().room),
                rooms = PreviewMocks.MeetingRooms().roomList,
                onRoomSelected = {},
            )
        }
    }
}

@Preview
@Composable
private fun RoomFormEmptyWithErrorPreview() {
    MeetingRoomBookingTheme {
        Surface {
            RoomForm(
                room = FieldState(value = null, error = RoomNameRequiredException()),
                rooms = PreviewMocks.MeetingRooms().roomList,
                onRoomSelected = {},
            )
        }
    }
}