package com.umnvd.booking.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.umnvd.booking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBackNavigationTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable() (RowScope.() -> Unit),
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIos,
                    contentDescription = stringResource(R.string.navigate_back_icon),
                )
            }
        },
        actions = actions,
    )
}