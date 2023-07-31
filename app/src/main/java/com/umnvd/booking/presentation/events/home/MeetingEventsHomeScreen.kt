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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.umnvd.booking.R
import com.umnvd.booking.core.navigation.navigations.EVENT_CALENDAR_ROUTE
import com.umnvd.booking.core.navigation.navigations.EVENT_SCHEDULE_GRAPH_ROUTE
import com.umnvd.booking.core.navigation.navigations.meetingEventScheduleGraph
import com.umnvd.booking.core.navigation.navigations.navigateToEventCalendar
import com.umnvd.booking.core.ui.components.AppFloatingActionButton
import com.umnvd.booking.core.ui.theme.MeetingRoomBookingTheme
import com.umnvd.booking.core.ui.viewmodels.SyncViewModel
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.presentation.events.home.components.WeekDaysHeader
import java.time.LocalDate

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MeetingEventsHomeScreen(
    syncViewModel: SyncViewModel = hiltViewModel(),
    onCreateClick: () -> Unit,
    navigateToEvent: (MeetingEventModel) -> Unit,
) {
    val sync = syncViewModel.sync.collectAsStateWithLifecycle()

    val navController = rememberAnimatedNavController()
    val currentBackStackEntry by navController
        .currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val calendarScreenShowing by remember {
        derivedStateOf {
            currentBackStackEntry?.destination?.route == EVENT_CALENDAR_ROUTE
        }
    }

    val currentDate = LocalDate.now().minusDays(1)


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
                                onClick = { navController.navigateToEventCalendar(currentDate) },
                            ) {
                                Text(text = "month_name")
                            }
                        }
                    }
                },
                actions = {
                    TextButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Text(text = "Today")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text(text = "My Events")
                    }
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
        MeetingEventsHomeScreen(
            onCreateClick = {},
            navigateToEvent = {},
        )
    }
}