package com.umnvd.booking.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.umnvd.booking.core.ui.utils.getText
import com.umnvd.booking.domain.AppException

val LocalAppErrorSnackbarController =
    compositionLocalOf { AppErrorSnackbarController {} }

class AppErrorSnackbarController(
    private val setError: (AppException?) -> Unit,
) {
    fun show(value: AppException?, onShown: () -> Unit) {
        if (value != null) {
            setError(value)
            onShown()
        }
    }
}

@Composable
fun AppErrorSnackbarProvider(
    content: @Composable (PaddingValues) -> Unit,
) {
    val (error, setError) = remember {
        mutableStateOf<AppException?>(null)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val errorSnackbarController = remember {
        AppErrorSnackbarController(setError)
    }

    LaunchedEffect(error) {
        if (error != null) {
            snackbarHostState.showSnackbar(error.getText(context))
            setError(null)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        CompositionLocalProvider(
            LocalAppErrorSnackbarController provides errorSnackbarController
        ) {
            content(it)
        }
    }
}