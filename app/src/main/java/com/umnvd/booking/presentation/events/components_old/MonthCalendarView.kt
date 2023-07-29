package com.umnvd.booking.presentation.events.components_old

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale as DateLocale

@Composable
fun MonthCalendarView(

) {
    val state = rememberCalendarState(
        startMonth = YearMonth.of(2023, 1),
        endMonth = YearMonth.of(2024, 1),
        firstVisibleMonth = YearMonth.now(),
        firstDayOfWeek = DayOfWeek.MONDAY,
    )
    Column {
        WeekDaysHeader()
        Spacer(modifier = Modifier.height(8.dp))
        VerticalCalendar(
            state = state,
            monthHeader = {
                val text = it.yearMonth.month.getDisplayName(
                    TextStyle.FULL_STANDALONE,
                    DateLocale.forLanguageTag(Locale.current.toLanguageTag()),
                ).capitalize(Locale.current)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 16.dp),
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.hint,
                    )
                }
            },
            dayContent = {
                if (it.position == DayPosition.MonthDate) {
                    MonthCalendarDayView(
                        day = it.date,
                        onClick = {},
                        today = it.date == LocalDate.now(),
                        hasEvents = it.date.dayOfMonth % 3 == 0,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}

@Preview(locale = "RU")
@Composable
private fun MonthCalendarViewPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            MonthCalendarView()
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun MonthCalendarViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            MonthCalendarView()
        }
    }
}