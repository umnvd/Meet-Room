package com.umnvd.booking.presentation.events

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hintOnSecondary
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.User
import com.umnvd.booking.util.debugPlaceholder
import com.umnvd.booking.util.mockMeetingEvent
import com.umnvd.booking.util.mockMeetingEventLongNames
import com.umnvd.booking.util.mockUserList
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCalendarEventView(
    event: MeetingEvent,
    modifier: Modifier = Modifier,
    onEventClick: ((MeetingEvent) -> Unit)? = null,
) {
    val halfHour = Duration.between(
        event.startAt, event.endAt
    ).toMinutes() == 30L

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ),
        onClick = { onEventClick?.invoke(event) },
        modifier = modifier,
    ) {
        if (halfHour) {
            HalfHourEventView(event = event)
        } else {
            DefaultEventView(event = event)
        }

    }

}

@Composable
private fun HalfHourEventView(
    event: MeetingEvent,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(0.dp, 192.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.hintOnSecondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = event.room.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.hintOnSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(0.dp, 200.dp),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Participants icon"
            )
            Text(
                text = event.participants.size.toString(),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun DefaultEventView(
    event: MeetingEvent,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 12.dp,
                end = 8.dp,
            ),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.hintOnSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = event.room.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.hintOnSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        UsersAvatarsView(
            users = event.participants,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

@Composable
private fun UsersAvatarsView(
    users: List<User>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val foldParticipants = users.size > 3
        val participantsMaxIndex =
            if (foldParticipants) 2
            else users.size - 1

        for (idx in 0..participantsMaxIndex) {
            val participant = users[idx]

            Box(
                modifier = Modifier
                    .padding(start = (16 * idx).dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(2.dp)
            ) {
                if (foldParticipants && idx >= 2) {
                    val restCount = users.size - 2

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(percent = 50)
                            )
                    ) {
                        Text(
                            text = "+$restCount",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 1.dp),
                        )
                    }
                } else {
                    AsyncImage(
                        model = participant.photoUrl,
                        contentDescription = "user $idx",
                        placeholder = debugPlaceholder(R.drawable.mock_user_avatar),
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(percent = 50))
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun DayCalendarEventViewHalfHourPreview() {
    MeetingRoomBookingTheme {
        Box(Modifier.height(32.dp)) {
            DayCalendarEventView(
                event = mockMeetingEvent.copy(
                    endAt = mockMeetingEvent.endAt.minusMinutes(30L),
                )
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarEventViewHourPreview() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Box(Modifier.height(64.dp)) {
            DayCalendarEventView(
                event = mockMeetingEvent.copy(
                    participants = mockUserList.dropLast(1),
                ),
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarEventViewTwoHoursPreview() {
    MeetingRoomBookingTheme {
        Box(Modifier.height(128.dp)) {
            DayCalendarEventView(
                event = mockMeetingEvent.copy(
                    endAt = mockMeetingEvent.endAt.plusHours(1),
                )
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarEventViewHalfHourLongNamesPreview() {
    MeetingRoomBookingTheme {
        Box(Modifier.height(32.dp)) {
            DayCalendarEventView(
                event = mockMeetingEventLongNames.copy(
                    endAt = mockMeetingEvent.endAt.minusMinutes(30L),
                )
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarEventViewHourLongNamesPreview() {
    MeetingRoomBookingTheme {
        Box(Modifier.height(64.dp)) {
            DayCalendarEventView(
                event = mockMeetingEventLongNames,
            )
        }
    }
}