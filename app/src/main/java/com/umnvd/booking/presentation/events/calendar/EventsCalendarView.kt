package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingColors
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale as DateLocale

@Composable
fun EventsCalendarView(

) {
    val state = rememberCalendarState(
        startMonth = YearMonth.of(2023, 1),
        endMonth = YearMonth.of(2024, 1),
        firstVisibleMonth = YearMonth.now(),
        firstDayOfWeek = DayOfWeek.MONDAY,
    )
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
                    .padding(top = 12.dp, bottom = 16.dp, start = 16.dp),
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = text,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        },
        dayContent = {
            MonthCalendarDayView(
                day = it.date,
                onClick = {},
                today = it.date == LocalDate.now(),
                hasEvents = it.date.dayOfMonth % 3 == 0,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    )
}

@Preview(locale = "RU")
@Composable
private fun EventsCalendarViewPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            Column {
                // TODO: extract from preview
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    DayOfWeek.values().forEach {
                        val text = it.getDisplayName(
                            TextStyle.SHORT,
                            DateLocale.forLanguageTag(Locale.current.toLanguageTag()),
                        ).toLowerCase(Locale.current)
                            .slice(0..0)

                        val holiday = it == DayOfWeek.SATURDAY || it == DayOfWeek.SUNDAY
                        val textColor =
                            if (holiday) MeetingRoomBookingColors.Gray400
                            else MaterialTheme.colors.onBackground

                        Text(
                            text = text,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp,
                            ),
                            color = textColor,
                        )
                    }
                }
                EventsCalendarView()
            }
        }
    }
}