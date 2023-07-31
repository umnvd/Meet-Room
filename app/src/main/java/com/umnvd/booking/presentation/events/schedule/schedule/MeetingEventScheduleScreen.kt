package com.umnvd.booking.presentation.events.schedule.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.events.consts.MAX_DATE
import com.umnvd.booking.domain.events.consts.MIN_DATE
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.common.widgets.CalendarDay
import com.umnvd.booking.presentation.events.schedule.calendar.widgets.EventSchedulePage
import com.umnvd.booking.presentation.events.schedule.schedule.viewmodel.MeetingEventScheduleScreenState
import com.umnvd.booking.presentation.events.schedule.schedule.viewmodel.MeetingEventScheduleScreenViewModel
import com.umnvd.booking.util.PreviewMocks
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun MeetingEventScheduleScreen(
    viewModel: MeetingEventScheduleScreenViewModel = hiltViewModel(),
    onEventCLick: (MeetingEventModel) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)

    MeetingEventScheduleScreenContent(
        state = state,
        onEventCLick = onEventCLick,
        onDateChanged = viewModel::setDate,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MeetingEventScheduleScreenContent(
    state: MeetingEventScheduleScreenState,
    onEventCLick: (MeetingEventModel) -> Unit = {},
    onDateChanged: (LocalDate) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val calendarState = rememberWeekCalendarState(
        startDate = MIN_DATE,
        endDate = MAX_DATE,
        firstVisibleWeekDate = state.date,
    )

    val pagerState = rememberPagerState(
        initialPage = pages[state.date]!!,
    )

    LaunchedEffect(pagerState.currentPage) {
        val date = calendarDates[pagerState.currentPage]
        calendarState.animateScrollToWeek(date)
        onDateChanged(date)
    }

    val onCalendarDayClick = remember {
        fun(date: LocalDate) {
            coroutineScope.launch {
                calendarState.scrollToWeek(date)
                pagerState.animateScrollToPage(pages[date]!!)
                onDateChanged(date)
            }
        }
    }

    Column {
        WeekCalendar(
            state = calendarState,
            dayContent = {
                val holiday = it.date.dayOfWeek == DayOfWeek.SATURDAY
                        || it.date.dayOfWeek == DayOfWeek.SUNDAY
                CalendarDay(
                    day = it.date,
                    onClick = onCalendarDayClick,
                    today = it.date == LocalDate.now(),
                    selected = it.date == state.date,
                    modifier = Modifier.align(Alignment.Center),
                    holiday = holiday
                )
            }
        )
        HorizontalPager(pageCount = calendarDates.size, state = pagerState) { _ ->
            EventSchedulePage(
                events = state.events,
                onEventClick = onEventCLick,
            )
        }
    }
}

private val calendarDates = List(
    size = 365,
    init = { MIN_DATE.plusDays(it.toLong()) }
)

private val pages = calendarDates
    .mapIndexed { index, localDate -> localDate to index }
    .toMap()


@Composable
private fun MeetingEventScheduleScreenContentPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxSize()) {
            MeetingEventScheduleScreenContent(
                state = MeetingEventScheduleScreenState(
                    date = PreviewMocks.MeetingEvents().date,
                    events = PreviewMocks.MeetingEvents().eventList,
                )
            )
        }
    }
}

@Preview(locale = "RU")
@Composable
private fun MeetingEventScheduleScreenContentPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(Modifier.fillMaxSize()) {
            MeetingEventScheduleScreenContent(
                state = MeetingEventScheduleScreenState(
                    date = PreviewMocks.MeetingEvents().date,
                    events = PreviewMocks.MeetingEvents().eventList,
                )
            )
        }
    }
}