package com.umnvd.booking.presentation.events.calendar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.presentation.events.EventFormView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAppBarView() {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.ArrowBackIos, contentDescription = "")
            }
        },
        actions = {
            Button(
                onClick = { /*TODO*/ },
//                contentPadding = PaddingValues(top = 4.dp, bottom = 5.dp),
                modifier = Modifier
//                    .defaultMinSize(minHeight = 24.dp)
                    .padding(end = 16.dp)
            ) {
                Text(text = "Save")
            }
        }
    )
}


@Preview
@Composable
private fun CalendarAppBarViewPreview() {
    MeetingRoomBookingTheme {
        Scaffold(
            topBar = {
                CalendarAppBarView()
            },
        ) {
            EventFormView(modifier = Modifier.padding(it))
        }
    }
}