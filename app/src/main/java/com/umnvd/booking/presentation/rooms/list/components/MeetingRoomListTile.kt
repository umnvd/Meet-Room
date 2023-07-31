package com.umnvd.booking.presentation.rooms.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.util.PreviewMocks

@Composable
fun MeetingRoomListTile(
    room: MeetingRoomModel,
    modifier: Modifier = Modifier,
    onRoomClick: (MeetingRoomModel) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable(onClick = { onRoomClick(room) })
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = room.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = stringResource(R.string.address_icon_description),
                tint = MaterialTheme.colorScheme.hint,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = room.address,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.hint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun MeetingRoomListTilePreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxWidth()) {
            MeetingRoomListTile(
                room = PreviewMocks.MeetingRooms().room,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun MeetingRoomListTileLongNamesPreview() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxWidth()) {
            MeetingRoomListTile(room = PreviewMocks.MeetingRooms().roomLongNames)
        }
    }
}