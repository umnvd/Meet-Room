package com.umnvd.booking.presentation.events.common.widgets.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.core.ui.components.AppTextField
import com.umnvd.booking.core.ui.models.FieldState
import com.umnvd.booking.core.ui.theme.divider
import com.umnvd.booking.core.ui.utils.text

@Composable
fun MainInfoForm(
    title: FieldState<String>,
    description: FieldState<String>,
    setTitle: (String) -> Unit,
    setDescription: (String) -> Unit,
) {
    AppTextField(
        placeholder = "Title",
        value = title.value,
        error = title.error?.text,
        onValueChange = setTitle,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 28.sp,
        ),
        modifier = Modifier.padding(start = 40.dp),
    )
    Divider(color = MaterialTheme.colorScheme.divider)
    Row {
        Icon(
            imageVector = Icons.Outlined.Notes,
            contentDescription = "",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        AppTextField(
            placeholder = "Description",
            value = description.value,
            error = description.error?.text,
            onValueChange = setDescription,
            modifier = Modifier.fillMaxWidth(),
            multiline = true,
        )
    }
}