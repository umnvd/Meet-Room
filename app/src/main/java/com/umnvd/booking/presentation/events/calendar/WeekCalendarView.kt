package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingColors
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle

val START_DATE: LocalDate = LocalDate.of(2023, 1, 1)
val END_DATE: LocalDate = START_DATE.plusDays(365)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekCalendarView() {
    val state = rememberWeekCalendarState(
        startDate = START_DATE,
        endDate = END_DATE,
        firstVisibleWeekDate = LocalDate.now(),
    )

    val dates = List(
        size = 365,
        init = {
            START_DATE.plusDays(it.toLong())
        }
    )

    val datesMap = dates.mapIndexed { index, localDate -> localDate to index }
        .toMap()
            
    val pagerState = rememberPagerState(
        initialPage = datesMap[LocalDate.now()]!!,
    )

    Column {
        WeekCalendar(
            state = state,
            dayContent = {
                val holiday = it.date.dayOfWeek == DayOfWeek.SATURDAY
                        || it.date.dayOfWeek == DayOfWeek.SUNDAY
                CalendarDayView(
                    day = it.date,
                    onClick = {},
                    today = it.date == LocalDate.now(),
                    selected = it.date == LocalDate.now(),
                    modifier = Modifier.align(Alignment.Center),
                    holiday = holiday
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(pageCount = dates.size, state = pagerState) { page ->
            Text(
                text = "Page: ${dates[page]}",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun WeekCalendarViewPreview() {
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
                            java.util.Locale.forLanguageTag(Locale.current.toLanguageTag()),
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
                WeekCalendarView()
            }
        }
    }
}