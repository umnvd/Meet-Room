package com.umnvd.booking.presentation.events.home.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.presentation.events.home.components.CalendarDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun MonthCalendarDay(
    day: LocalDate,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    today: Boolean = false,
    selected: Boolean = false,
    hasEvents: Boolean = false,
) {
    val holiday = day.dayOfWeek == DayOfWeek.SATURDAY
            || day.dayOfWeek == DayOfWeek.SUNDAY

    Column(
        modifier = modifier.height(48.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        CalendarDay(
            day = day,
            onClick = onClick,
            today = today,
            selected = selected,
            holiday = holiday,
        )
        Box(Modifier.weight(1f)) {
            if (hasEvents) Box(
                Modifier
                    .size(6.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(percent = 50),
                    )
                    .align(Alignment.Center)
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.divider,
            thickness = 1.dp,
        )
    }
}

@Preview(locale = "RU")
@Composable
private fun MonthCalendarDayViewPreview() {
    MeetingRoomBookingTheme {
        Surface {
            MonthCalendarDay(
                day = LocalDate.now(),
                today = true,
                onClick = {},
                hasEvents = true,
            )
        }
    }
}

