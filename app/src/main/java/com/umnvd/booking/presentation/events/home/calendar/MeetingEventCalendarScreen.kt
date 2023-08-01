package com.umnvd.booking.presentation.events.home.calendar

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.domain.events.consts.MAX_DATE
import com.umnvd.booking.domain.events.consts.MIN_DATE
import com.umnvd.booking.presentation.events.home.calendar.components.MonthCalendarDay
import com.umnvd.booking.presentation.events.home.viewmodel.MeetingEventsHomeScreenViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MeetingEventCalendarScreen(
    viewModel: MeetingEventsHomeScreenViewModel,
    onDayClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onDayClickHandler = remember {
        fun(value: LocalDate) {
            viewModel.setDate(value)
            onDayClick()
        }
    }

    MeetingEventCalendarScreenContent(
        eventDays = state.eventDays,
        currentDate = state.date,
        onDayClick = onDayClickHandler,
    )
}

@Composable
fun MeetingEventCalendarScreenContent(
    eventDays: Set<LocalDate>,
    currentDate: LocalDate,
    onDayClick: (LocalDate) -> Unit = {},
) {
    val calendarState = rememberCalendarState(
        startMonth = YearMonth.of(MIN_DATE.year, MIN_DATE.month),
        endMonth = YearMonth.of(MAX_DATE.year, MAX_DATE.month),
        firstVisibleMonth = YearMonth.of(currentDate.year, currentDate.month),
        firstDayOfWeek = DayOfWeek.MONDAY,
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        )
        VerticalCalendar(
            state = calendarState,
            monthHeader = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 16.dp),
                ) {
                    Text(
                        text = it.yearMonth.month.text,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.hint,
                    )
                }
            },
            dayContent = {
                if (it.position == DayPosition.MonthDate) {
                    MonthCalendarDay(
                        day = it.date,
                        onClick = onDayClick,
                        today = it.date == LocalDate.now(),
                        selected = it.date == currentDate,
                        hasEvents = eventDays.contains(it.date),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}


@Preview(locale = "ru")
@Composable
fun MeetingEventCalendarScreenContentPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            MeetingEventCalendarScreenContent(
                eventDays = setOf(
                    LocalDate.now().minusDays(2),
                    LocalDate.now().plusDays(1)
                ),
                currentDate = LocalDate.now(),
            )
        }
    }
}

@Preview
@Composable
fun MeetingEventCalendarScreenContentPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            MeetingEventCalendarScreenContent(
                eventDays = setOf(
                    LocalDate.now().minusDays(2),
                    LocalDate.now().plusDays(1)
                ),
                currentDate = LocalDate.now(),
            )
        }
    }
}