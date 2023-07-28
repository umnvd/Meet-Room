package com.umnvd.booking.presentation.events

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.theme.hint
import com.umnvd.booking.core.ui.utils.debugPlaceholder
import com.umnvd.booking.util.PreviewMocks
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EventFormView(
    modifier: Modifier = Modifier,
) {
    val (text, setText) = remember {
        mutableStateOf("")
    }
    val (text2, setText2) = remember {
        mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
    }

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    if (isDateDialogShown) {
        DatePickerDialog(
            onDismissRequest = { isDateDialogShown = false },
            onDateChange = {
                Log.d("DATE_TIME", it.toString())
                isDateDialogShown = false
            },
            title = { Text(text = "Select date") },
            initialDate = LocalDate.now(),
        )
    }
    var isTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    if (isTimeDialogShown) {
        TimePickerDialog(

            onDismissRequest = { isTimeDialogShown = false },
            onTimeChange = {
                Log.d("DATE_TIME", it.toString())
                isTimeDialogShown = false
            },
            title = { Text(text = "Select date") },
            is24HourFormat = true,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTextField(
            value = text,
            onValueChange = setText,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 28.sp,
            ),
            modifier = Modifier.padding(start = 40.dp),
            placeholder = "Title",
            error = "Title is required",
        )
        Divider(color = MaterialTheme.colorScheme.divider)
        Row {
            Icon(
                imageVector = Icons.Outlined.Notes,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            AppTextField(
                value = text2,
                onValueChange = setText2,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Description",
                multiline = true,
            )
        }
        Divider(color = MaterialTheme.colorScheme.divider)
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
                        text = "Start date",
                        modifier = Modifier.clickable(
                            onClick = { isDateDialogShown = true }
                        ),
                    )
                    Text(
                        text = "Start time",
                        modifier = Modifier.clickable(
                            onClick = { isTimeDialogShown = true }
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "End date",
//                        modifier = Modifier.clickable(
//                            onClick = datePickerDialogState::show
//                        ),
                    )
                    Text(
                        text = "End time",
//                        modifier = Modifier.clickable(
//                            onClick = timePickerDialogState::show
//                        ),
                    )
                }
            }
        }
        Divider(color = MaterialTheme.colorScheme.divider)
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.MeetingRoom,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            AppTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Select room",
                error = "Room is required",
            )
        }
        Divider(color = MaterialTheme.colorScheme.divider)
        Row {
            Icon(
                imageVector = Icons.Outlined.Group,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            Column(
                modifier = Modifier.padding(top = 4.dp, end = 8.dp),
            ) {
                for (user in PreviewMocks.Users().usersList) {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(modifier = Modifier.weight(1f)) {
                            AsyncImage(
                                model = user.photoUrl,
                                contentDescription = "user ${user.uid}",
                                placeholder = debugPlaceholder(R.drawable.mock_user_avatar),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(percent = 50))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = user.fullName)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.hint,
                            )
                        }
                    }
                }
                AppTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Select participants",
                )
            }
        }
    }
}


@Preview
@Composable
private fun EventFormViewPreview() {
    MeetingRoomBookingTheme {
        Surface {
            EventFormView()
        }
    }
}

@Preview
@Composable
private fun EventFormViewPreviewDark() {
    MeetingRoomBookingTheme(darkTheme = true) {
        Surface {
            EventFormView()
        }
    }
}