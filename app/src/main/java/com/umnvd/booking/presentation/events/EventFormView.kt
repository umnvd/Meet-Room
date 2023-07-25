package com.umnvd.booking.presentation.events

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.umnvd.booking.R
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.domain.models.mockUserList
import com.umnvd.booking.util.debugPlaceholder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventFormView() {
    val (text, setText) = remember {
        mutableStateOf("")
    }
    val (text2, setText2) = remember {
        mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua")
    }

    Column(
        modifier = Modifier
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
        Divider(color = Color.LightGray)
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
        Divider(color = Color.LightGray)
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
                    Text(text = "Start date")
                    Text(text = "Start time")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "End date")
                    Text(text = "End time")
                }
            }
        }
        Divider(color = Color.LightGray)
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            AppTextField(
                value = "",
                onValueChange = {  },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Select room",
                error = "Room is required",
            )
        }
        Divider(color = Color.LightGray)
        Row() {
            Icon(
                imageVector = Icons.Outlined.Group,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            )
            Column(
                modifier = Modifier.padding(top = 4.dp),
            ) {
                for (user in mockUserList) {
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