package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.models.mockMeetingEventList
import java.time.DayOfWeek
import java.time.LocalDate

val START_DATE: LocalDate = LocalDate.of(2023, 1, 1)
val END_DATE: LocalDate = START_DATE.plusDays(365)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekCalendarView(
    modifier: Modifier = Modifier,
) {
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

    Column(modifier) {
        WeekDaysHeader()
        Spacer(modifier = Modifier.height(8.dp))
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
        HorizontalPager(pageCount = dates.size, state = pagerState) { page ->
            DayCalendarView(events = mockMeetingEventList)
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun WeekCalendarViewPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            Column {
                WeekCalendarView()
            }
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun WeekCalendarViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            Column {
                WeekCalendarView()
            }
        }
    }
}