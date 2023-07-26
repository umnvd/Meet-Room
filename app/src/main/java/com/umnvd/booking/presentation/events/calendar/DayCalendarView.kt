package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.domain.models.MeetingEvent
import com.umnvd.booking.presentation.events.DayCalendarEventView
import com.umnvd.booking.util.mockMeetingEventList
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

    Row(modifier = modifier.verticalScroll(rememberScrollState())) {
        DayCalendarSidebar(hourHeight = hourHeight)
        DayCalendarLayout(hourHeight = hourHeight) {
            events.sortedBy(MeetingEvent::startAt).forEach {
                DayCalendarEventView(
                    event = it,
                    onEventClick = onEventClick,
                    modifier = Modifier.parentData(it),
                )
            }

        }
    }
}

@Composable
private fun DayCalendarLayout(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable @UiComposable () -> Unit,
) {
    val dividerColor = MaterialTheme.colorScheme.divider

    Layout(
        content = content,
        modifier = modifier
            .drawBehind {
                repeat(23) {
                    val yOffset = (it + 1.25f) * hourHeight.toPx()
                    drawLine(
                        color = dividerColor,
                        start = Offset(-16f, yOffset),
                        end = Offset(size.width, yOffset),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                drawLine(
                    color = dividerColor,
                    start = Offset(2f, 0f),
                    end = Offset(2f, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            },
    ) { measureables, constraints ->
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

        val layoutHeight = hourHeight.roundToPx() * 24 + hourHeight.roundToPx() / 2
        layout(constraints.maxWidth, layoutHeight) {
            placeablesWithEvents.forEach { (placeable, event) ->
                val eventOffsetMinutes = ChronoUnit.MINUTES.between(
                    LocalTime.MIN,
                    event.startAt.toLocalTime()
                )
                val eventOffset = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val eventY = (hourHeight.roundToPx() / 4) + eventOffset + 2
                placeable.place(0, eventY)
            }
        }
    }
}

@Composable
fun DayCalendarSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(56.dp),
    ) {
        val startTime = LocalTime.MIN
        Box(modifier = Modifier.height(hourHeight * 0.75f))
        repeat(23) { i ->
            Box(modifier = Modifier.height(hourHeight)) {
                val time = startTime.plusHours(i.toLong() + 1L)
                Text(
                    text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.hint,
                    modifier = modifier.align(Alignment.Center),
                )
            }
        }
        Box(modifier = Modifier.height(hourHeight * 0.75f))
    }
}

private fun Modifier.parentData(data: Any?) =
    this.then(object : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any? = data
    })


@Preview
@Composable
private fun DayCalendarViewPreview() {
    MeetingRoomBookingTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DayCalendarView(
                events = mockMeetingEventList,
            )
        }
    }
}

@Preview
@Composable
private fun DayCalendarViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            DayCalendarView(
                events = mockMeetingEventList,
            )
        }
    }
}