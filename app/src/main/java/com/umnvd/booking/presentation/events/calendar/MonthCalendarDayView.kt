package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun MonthCalendarDayView(
    day: LocalDate,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    today: Boolean = false,
    hasEvents: Boolean = false,
) {
    val holiday = day.dayOfWeek == DayOfWeek.SATURDAY
            || day.dayOfWeek == DayOfWeek.SUNDAY

    Column(
        modifier = modifier.height(36.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CalendarDayView(
            day = day,
            onClick = onClick,
            today = today,
            selected = today,
            holiday = holiday,
        )
        Box(Modifier.fillMaxHeight()) {
            if (hasEvents) Box(
                Modifier
                    .size(4.dp)
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(percent = 50),
                    )
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun MonthCalendarDayViewPreview() {
    MeetingRoomBookingTheme {
        Surface {
            MonthCalendarDayView(
                day = LocalDate.now(),
                today = true,
                onClick = {},
                hasEvents = true,
            )
        }
    }
}

