package com.umnvd.booking.presentation.events.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppErrorText
import com.umnvd.booking.core.ui.components.AppRadioButton
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.debugPlaceholder
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.util.PreviewMocks
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsForm(
    participants: FieldState<List<UserModel>>,
    users: List<UserModel>,
    onParticipantSelected: (UserModel) -> Unit,
    onParticipantRemoved: (UserModel) -> Unit,
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
                items(items = users, key = UserModel::uid) {
                    ParticipantTile(
                        participant = it,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 8.dp),
                        action = {
                            AppRadioButton(
                                checked = participants.value.contains(it),
                                onChange = { value ->
                                    if (value) onParticipantSelected(it)
                                    else onParticipantRemoved(it)
                                }
                            )
                        }
                    )
                    Divider(color = MaterialTheme.colorScheme.divider)
                }
            }
        }
    }

    Row {
        Icon(
            imageVector = Icons.Outlined.Group,
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        Column(
            modifier = Modifier.padding(top = 4.dp, end = 8.dp),
        ) {
            for (participant in participants.value) {
                ParticipantTile(
                    participant = participant,
                    modifier = Modifier.padding(start = 16.dp),
                    action = {
                        IconButton(onClick = { onParticipantRemoved(participant) }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.clear_icon_description),
                                tint = MaterialTheme.colorScheme.hint,
                            )
                        }
                    }
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.event_select_participants_hint),
                    color = MaterialTheme.colorScheme.hint,
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                )
                if (participants.error != null) {
                    AppErrorText(
                        text = participants.error.text,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 2.dp
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun ParticipantTile(
    participant: UserModel,
    modifier: Modifier = Modifier,
    action: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = participant.photoUrl,
                contentDescription = null,
                placeholder = debugPlaceholder(R.drawable.mock_user_avatar),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(percent = 50))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = participant.fullName,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        action()
    }
}


@Preview
@Composable
private fun ParticipantsFormPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            ParticipantsForm(
                participants = FieldState(
                    PreviewMocks.Users().userList.slice(0..1)
                ),
                users = PreviewMocks.Users().userList,
                onParticipantSelected = {},
                onParticipantRemoved = {},
            )
        }
    }
}