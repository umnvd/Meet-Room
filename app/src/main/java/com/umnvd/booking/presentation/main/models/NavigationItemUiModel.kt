package com.umnvd.booking.presentation.main.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItemUiModel(
    @StringRes val titleId: Int,
    val icon: ImageVector,
    val route: String
)
