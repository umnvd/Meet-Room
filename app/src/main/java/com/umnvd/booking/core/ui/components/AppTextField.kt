package com.umnvd.booking.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingColors
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    multiline: Boolean = false,
    maxLines: Int = if (multiline) 5 else 1,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var hasFocus by remember { mutableStateOf(false) }
    val clearButtonVisible by remember(value) {
        derivedStateOf { value.isNotEmpty() && hasFocus }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        label?.let {
            Text(
                text = it,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 4.dp
                ),
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { hasFocus = it.hasFocus },
            enabled = enabled,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = !multiline,
            maxLines = maxLines,
            minLines = minLines,
            trailingIcon = if (clearButtonVisible) {
                { IconButton(onClick = { onValueChange("")} ) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "Clear",
                    )
                }}
            } else null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                trailingIconColor = MeetingRoomBookingColors.Gray400
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        error?.let {
            Text(
                text = it,
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.error
                ),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            )
        }
    }
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    MeetingRoomBookingTheme {
        Surface(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                AppTextField(
                    value = "Initial value",
                    label = "Label",
                    onValueChange = {},
                    error = "Error message",

                    )
            }
        }
    }
}