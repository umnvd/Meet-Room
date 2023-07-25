package com.umnvd.booking.presentation.events

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingColors
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.mockMeetingEvent
import com.umnvd.booking.domain.models.mockMeetingEventLongNames
import com.umnvd.booking.util.debugPlaceholder
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun DayCalendarEventView(
    event: MeetingEvent,
    modifier: Modifier = Modifier,
    onEventClick: ((MeetingEvent) -> Unit)? = null,
) {
    val durationMinutes = Duration.between(event.startAt, event.endAt).toMinutes()
    val halfHour = durationMinutes == 30L

    val eventTextStyle = LocalTextStyle.current.copy(
        fontSize = 12.sp,
    )

    val roomTextStyle = LocalTextStyle.current.copy(
        color = MeetingRoomBookingColors.Gray400,
        fontSize = 12.sp,
    )

    val participantsTextStyle = LocalTextStyle.current.copy(
        fontSize = 12.sp,
    )

    Row(
        modifier = modifier
            .clickable { onEventClick?.invoke(event) }
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (halfHour) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = eventTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(0.dp, 200.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = event.room.name,
                    style = roomTextStyle,
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
                    style = participantsTextStyle,
                )
            }
        } else {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.title,
                    style = eventTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.room.name,
                    style = roomTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                val participantsMaxIndex =
                    if (event.participants.size >= 3) 2
                    else event.participants.size - 1

                for (idx in 0..participantsMaxIndex) {
                    val participant = event.participants[idx]

                    Box(
                        modifier = Modifier
                            .padding(start = (16 * idx).dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.surface,
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .padding(1.dp)
                    ) {


                        if (idx == participantsMaxIndex) {
                            val restCount = 1

                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = MaterialTheme.colors.primary,
                                        shape = RoundedCornerShape(percent = 50)
                                    )
                            ) {
                                Text(
                                    text = "+$restCount",
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    style = participantsTextStyle.copy(
                                        color = MaterialTheme.colors.onPrimary,
                                    ),
                                    textAlign = TextAlign.Center,
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
    }

}


@Preview
@Composable
private fun DayCalendarEventViewHalfHourPreview() {
    MeetingRoomBookingTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
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
    MeetingRoomBookingTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            DayCalendarEventView(
                event = mockMeetingEvent,
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarEventViewTwoHoursPreview() {
    MeetingRoomBookingTheme {
        Box(
            Modifier
                .fillMaxWidth()
                .height(128.dp)
        ) {
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
        Box(
            Modifier
                .fillMaxWidth()
                .height(32.dp)
        ) {
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
        Box(
            Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            DayCalendarEventView(
                event = mockMeetingEventLongNames,
            )
        }
    }
}