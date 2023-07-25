package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingColors
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import java.time.LocalDate

@Composable
fun CalendarDayView(
    day: LocalDate,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    today: Boolean = false,
    holiday: Boolean = false,
    selected: Boolean = false,
) {
    val dayTextColor = when {
        selected -> {
            if (today) MaterialTheme.colors.onPrimary
            else MaterialTheme.colors.background
        }

        today -> {
            MaterialTheme.colors.primary
        }

        holiday -> {
            MeetingRoomBookingColors.Gray400
        }

        else -> MaterialTheme.colors.onBackground
    }

    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(percent = 50))
            .composed {
                if (selected) {
                    if (today) {
                        background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(percent = 50)
                        )
                    } else {
                        background(
                            color = MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(percent = 50)
                        )
                    }
                } else this
            }
            .clickable(onClick = { onClick(day) }),
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = dayTextColor,
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun CalendarDayViewPreview() {
    MeetingRoomBookingTheme {
        Surface {
            CalendarDayView(
                day = LocalDate.now(),
                onClick = {},
                today = true,
                selected = true,
            )
        }
    }
}

