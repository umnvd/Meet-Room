package com.umnvd.booking.presentation.events.components_old

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.umnvd.booking.core.ui.theme.hint
import java.time.DayOfWeek
import java.time.format.TextStyle

@Composable
fun WeekDaysHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
                if (holiday) MaterialTheme.colorScheme.hint
                else MaterialTheme.colorScheme.onBackground

            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
            )
        }
    }
}