package com.umnvd.booking.presentation.events.common.components.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import com.umnvd.booking.core.ui.models.FieldState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.ui.text.intl.Locale.Companion as AndroidLocale

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DurationForm(
    startDate: FieldState<LocalDate>,
    startTime: FieldState<LocalTime>,
    endDate: FieldState<LocalDate>,
    endTime: FieldState<LocalTime>,
    onStartDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
) {
    val dateFormatter = remember {
        DateTimeFormatter.ofPattern(
            "EE, dd.MM.yyyy",
            Locale.forLanguageTag(AndroidLocale.current.toLanguageTag())
        )
    }
    val timeFormatter = remember {
        DateTimeFormatter.ofPattern("HH:mm")
    }

    var startDateDialogShowing: Boolean by rememberSaveable { mutableStateOf(false) }
    var startTimeDialogShowing: Boolean by rememberSaveable { mutableStateOf(false) }
    var endDateDialogShowing: Boolean by rememberSaveable { mutableStateOf(false) }
    var endTimeDialogShowing: Boolean by rememberSaveable { mutableStateOf(false) }

    if (startDateDialogShowing) {
        DatePickerDialog(
            onDismissRequest = { startDateDialogShowing = false },
            onDateChange = {
                onStartDateChanged(it)
                startDateDialogShowing = false
            },
            title = { Text(text = "Select start date") },
            initialDate = startDate.value,
        )
    }

    if (startTimeDialogShowing) {
        TimePickerDialog(
            onDismissRequest = { startTimeDialogShowing = false },
            onTimeChange = {
                onStartTimeChanged(it)
                startTimeDialogShowing = false
            },
            title = { Text(text = "Select start time") },
            is24HourFormat = true,
            initialTime = startTime.value
        )
    }

    if (endDateDialogShowing) {
        DatePickerDialog(
            onDismissRequest = { endDateDialogShowing = false },
            onDateChange = {
                onEndDateChanged(it)
                endDateDialogShowing = false
            },
            title = { Text(text = "Select end date") },
            initialDate = endDate.value,
        )
    }

    if (endTimeDialogShowing) {
        TimePickerDialog(
            onDismissRequest = { endTimeDialogShowing = false },
            onTimeChange = {
                onEndTimeChanged(it)
                endTimeDialogShowing = false
            },
            title = { Text(text = "Select end time") },
            is24HourFormat = true,
            initialTime = endTime.value
        )
    }

    Row(
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = "",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = startDate.value
                        .format(dateFormatter)
                        .capitalize(AndroidLocale.current),
                    modifier = Modifier.clickable(onClick = { startDateDialogShowing = true }),
                )
                Text(
                    text = startTime.value.format(timeFormatter),
                    modifier = Modifier.clickable(onClick = { startTimeDialogShowing = true }),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = endDate.value
                        .format(dateFormatter)
                        .capitalize(AndroidLocale.current),
                    modifier = Modifier.clickable(onClick = { endDateDialogShowing = true }),
                )
                Text(
                    text = endTime.value.format(timeFormatter),
                    modifier = Modifier.clickable(onClick = { endTimeDialogShowing = true }),
                )
            }
        }
    }
}