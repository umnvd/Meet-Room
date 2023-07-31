package com.umnvd.booking.presentation.events.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.hint
import java.time.LocalDate

@Composable
fun CalendarDay(
    day: LocalDate,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    today: Boolean = false,
    holiday: Boolean = false,
    selected: Boolean = false,
) {
    val dayTextColor = when {
        selected -> {
            if (today) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.background
        }

        today -> {
            MaterialTheme.colorScheme.primary
        }

        holiday -> {
            MaterialTheme.colorScheme.hint
        }

        else -> MaterialTheme.colorScheme.onBackground
    }

    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(percent = 50))
            .composed {
                if (selected) {
                    if (today) {
                        background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(percent = 50)
                        )
                    } else {
                        background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(percent = 50)
                        )
                    }
                } else this
            }
            .clickable(onClick = { onClick(day) }),
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = dayTextColor,
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 1.dp)
        )
    }
}

@Preview
@Composable
private fun CalendarDayViewPreview() {
    MeetingRoomBookingTheme {
        Surface {
            CalendarDay(
                day = LocalDate.now(),
                onClick = {},
                today = true,
                selected = true,
            )
        }
    }
}

