package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.domain.models.mockMeetingEvent
import com.umnvd.booking.presentation.events.DayCalendarEventView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
fun DayCalendarView(
    events: List<MeetingEvent>,
    modifier: Modifier = Modifier,
    onEventClick: ((MeetingEvent) -> Unit)? = null,
) {
    val hourHeight = 64.dp
//    val verticalScrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        ScheduleSidebar(
            hourHeight = hourHeight,
        )
        Layout(
            content = {
                events.sortedBy(MeetingEvent::startAt).forEach {
                    DayCalendarEventView(
                        event = it,
                        modifier = Modifier.eventData(it)
                    )
                }
            },
            modifier = modifier
                .drawBehind {
                    repeat(23) {
                        drawLine(
                            Color.Black,
                            start = Offset(-16f, (it + 1) * hourHeight.toPx()),
                            end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    drawLine(
                        Color.Black,
                        start = Offset(2f, 0f),
                        end = Offset(2f, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
        ) { measureables, constraints ->
            val height = hourHeight.roundToPx().let { it * 24 + it / 2 }
            val placeablesWithEvents = measureables.map { measurable ->
                val event = measurable.parentData as MeetingEvent
                val eventDurationMinutes = ChronoUnit.MINUTES.between(event.startAt, event.endAt)
                val eventHeight = ((eventDurationMinutes / 60f) * hourHeight.toPx()).roundToInt() - 4
                val eventWidth = (constraints.maxWidth.toDp() - 16.dp).toPx().toInt()
                val placeable = measurable.measure(
                    constraints.copy(
                        minHeight = eventHeight,
                        maxHeight = eventHeight,
                        minWidth = eventWidth,
                        maxWidth = eventWidth,
                    )
                )
                Pair(placeable, event)
            }

            layout(constraints.maxWidth, height) {
                placeablesWithEvents.forEach { (placeable, event) ->
                    val eventOffsetMinutes =
                        ChronoUnit.MINUTES.between(LocalTime.MIN, event.startAt.toLocalTime())
                    val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt() + 2
                    placeable.place(0, eventY)
                }
            }
        }
    }
}

private class EventDataModifier(
    val event: MeetingEvent,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = event
}

private fun Modifier.eventData(event: MeetingEvent) =
    this.then(EventDataModifier(event))

@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(56.dp),
    ) {
        val startTime = LocalTime.MIN
        Box(modifier =Modifier.height(hourHeight / 2))
        repeat(23) { i ->
            Box(modifier = Modifier.height(hourHeight)) {
                val time = startTime.plusHours(i.toLong() + 1L)
                Text(
                    text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                    ),
                    modifier = modifier.align(Alignment.Center),
                )
            }
        }
        Box(modifier =Modifier.height(hourHeight / 2))
    }
}


@Preview
@Composable
private fun DayCalendarViewPreview() {
    MeetingRoomBookingTheme {
        Surface(color = Color.LightGray, modifier = Modifier.fillMaxSize()) {
            DayCalendarView(
                events = listOf(
                    mockMeetingEvent,
                    mockMeetingEvent.copy(
                        startAt = mockMeetingEvent.startAt.plusMinutes(90L),
                        endAt = mockMeetingEvent.startAt.plusMinutes(120L),
                    ),
                    mockMeetingEvent.copy(
                        startAt = mockMeetingEvent.startAt.plusHours(4),
                        endAt = mockMeetingEvent.startAt.plusHours(6),
                    ),
                    mockMeetingEvent.copy(
                        startAt = LocalDateTime.of(
                            LocalDate.now(),
                            LocalTime.of(0, 0),
                        ),
                        endAt =LocalDateTime.of(
                            LocalDate.now(),
                            LocalTime.of(1, 0),
                        ),
                    ),
                    mockMeetingEvent.copy(
                        startAt =LocalDateTime.of(
                            LocalDate.now(),
                            LocalTime.of(23, 0),
                        ),
                        endAt = LocalDateTime.of(
                            LocalDate.now().plusDays(1),
                            LocalTime.of(0, 0),
                        ),
                    ),
                ),
            )
        }
    }
}