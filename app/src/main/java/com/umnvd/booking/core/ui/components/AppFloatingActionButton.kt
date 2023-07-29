package com.umnvd.booking.core.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults
            .bottomAppBarFabElevation(defaultElevation = 0.dp),
        containerColor = containerColor,
        modifier = modifier,
        content = content,
    )
}