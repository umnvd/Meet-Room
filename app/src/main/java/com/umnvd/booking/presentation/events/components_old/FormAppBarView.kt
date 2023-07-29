package com.umnvd.booking.presentation.events.components_old

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAppBarView() {
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
private fun FormAppBarViewPreview() {
    MeetingRoomBookingTheme {
        Scaffold(
            topBar = {
                FormAppBarView()
            },
        ) {
            EventFormView(modifier = Modifier.padding(it))
        }
    }
}