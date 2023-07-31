package com.umnvd.booking.presentation.events.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.R
import com.umnvd.booking.core.navigation.navigations.EVENT_CALENDAR_ROUTE
import com.umnvd.booking.core.navigation.navigations.EVENT_SCHEDULE_GRAPH_ROUTE
import com.umnvd.booking.core.navigation.navigations.meetingEventScheduleGraph
import com.umnvd.booking.core.navigation.navigations.navigateToEventCalendar
import com.umnvd.booking.core.navigation.navigations.navigateToEventSchedule
import com.umnvd.booking.core.ui.components.AppFloatingActionButton
import com.umnvd.booking.core.ui.components.LocalAppErrorSnackbarController
import com.umnvd.booking.core.ui.components.LocalAppProgressIndicatorController
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.utils.text
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.home.components.WeekDaysHeader
import com.umnvd.booking.presentation.events.home.viewmodel.MeetingEventsHomeScreenViewModel
import java.time.LocalDate


@Composable
fun MeetingEventsHomeScreen(
    viewModel: MeetingEventsHomeScreenViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
    onCreateClick: () -> Unit,
    onMyEventsClick: () -> Unit,
    navigateToEvent: (MeetingEventModel) -> Unit,
    homeNavController: NavHostController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sync by syncViewModel.sync.collectAsStateWithLifecycle()

    LocalAppProgressIndicatorController.current.state(state.loading)
    LocalAppErrorSnackbarController.current.show(state.error, viewModel::errorHandled)

    LaunchedEffect(sync) {
        if (sync) {
            viewModel.loadEvents()
            syncViewModel.syncHandled()
        }
    }

    MeetingEventsHomeScreenContent(
        selectedDate = state.date,
        onCreateClick = onCreateClick,
        onTodayClick = viewModel::setToday,
        onMyEventsClick = onMyEventsClick,
        navigateToEvent = navigateToEvent,
        homeNavController = homeNavController,
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MeetingEventsHomeScreenContent(
    selectedDate: LocalDate,
    onCreateClick: () -> Unit,
    onTodayClick: () -> Unit,
    onMyEventsClick: () -> Unit,
    navigateToEvent: (MeetingEventModel) -> Unit,
    homeNavController: NavHostController,
) {
    val navController = rememberAnimatedNavController()
    val currentBackStackEntry by navController
        .currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val calendarScreenShowing by remember {
        derivedStateOf {
            currentBackStackEntry?.destination?.route == EVENT_CALENDAR_ROUTE
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Crossfade(
                        targetState = calendarScreenShowing,
                        label = "events_nav_button_anim"
                    ) {
                        if (it) {
                            IconButton(onClick = navController::popBackStack) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBackIos,
                                    contentDescription = stringResource(R.string.navigate_back_icon),
                                )
                            }
                        } else {
                            TextButton(
                                onClick = navController::navigateToEventCalendar,
                            ) {
                                Text(text = selectedDate.month.text.capitalize(Locale.current))
                            }
                        }
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            onTodayClick()
                            navController.navigateToEventSchedule()
                        },
                    ) {
                        Text(text = stringResource(R.string.events_today_button))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onMyEventsClick) {
                        Text(text = stringResource(R.string.events_my_events_button))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = onCreateClick,
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            WeekDaysHeader()
            AnimatedNavHost(
                navController = navController,
                startDestination = EVENT_SCHEDULE_GRAPH_ROUTE,
            ) {
                meetingEventScheduleGraph(
                    navController = navController,
                    homeNavController = homeNavController,
                    navigateToEvent = navigateToEvent,
                )
            }
        }
    }
}


@Preview
@Composable
private fun MeetingEventsHomeScreenPreview() {
    MeetingRoomBookingTheme {
        MeetingEventsHomeScreenContent(
            selectedDate = LocalDate.now(),
            onCreateClick = {},
            navigateToEvent = {},
            onMyEventsClick = {},
            onTodayClick = {},
            homeNavController = rememberNavController(),
        )
    }
}