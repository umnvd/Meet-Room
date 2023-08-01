package com.umnvd.booking.presentation.events.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umnvd.booking.R
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
    val focusManager = LocalFocusManager.current

    AppTextField(
        placeholder = stringResource(R.string.event_title_hint),
        value = title.value,
        error = title.error?.text,
        onValueChange = setTitle,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 28.sp,
        ),
        modifier = Modifier.padding(start = 40.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences,
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
    )
    Divider(color = MaterialTheme.colorScheme.divider)
    Row {
        Icon(
            imageVector = Icons.Outlined.Notes,
            contentDescription = stringResource(R.string.additional_info_icon_description),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        AppTextField(
            placeholder = stringResource(R.string.event_additional_info_hint),
            value = description.value,
            error = description.error?.text,
            onValueChange = setDescription,
            modifier = Modifier.fillMaxWidth(),
            multiline = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus(true) }
            ),
        )
    }
}