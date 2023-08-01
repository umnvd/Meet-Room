package com.umnvd.booking.core.navigation.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.main.viewmodel.MainViewModel
import com.umnvd.booking.presentation.rooms.creation.MeetingRoomCreationScreen
import com.umnvd.booking.presentation.rooms.list.MeetingRoomListScreen
import com.umnvd.booking.presentation.rooms.room.MeetingRoomScreen

const val ROOMS_GRAPH_ROUTE = "rooms_graph"
const val ROOM_ROUTE_UID_KEY = "uid"

private const val ROOM_LIST_ROUTE = "rooms"
private const val ROOM_ROUTE_BASE = "room"
private const val ROOM_ROUTE = "$ROOM_ROUTE_BASE/{$ROOM_ROUTE_UID_KEY}"
private const val CREATE_ROOM_ROUTE = "create_room"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingRoomsGraph(
    navHostController: NavHostController,
) {
    navigation(startDestination = ROOM_LIST_ROUTE, route = ROOMS_GRAPH_ROUTE) {
        composable(route = ROOM_LIST_ROUTE) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingRoomListScreen(
                mainViewModel = mainViewModel,
                onRoomClick = { navHostController.navigate("$ROOM_ROUTE_BASE/${it.uid}") },
                onCreateClick = { navHostController.navigate(CREATE_ROOM_ROUTE) },
            )
        }
        composable(
            route = ROOM_ROUTE,
            arguments = listOf(navArgument(ROOM_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingRoomScreen(
                mainViewModel = mainViewModel,
                onSaved = navHostController::popBackStack,
                onDeleted = navHostController::popBackStack,
                onBackClick = navHostController::popBackStack,
            )
        }
        composable(route = CREATE_ROOM_ROUTE) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(EVENTS_HOME_ROUTE)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            MeetingRoomCreationScreen(
                mainViewModel = mainViewModel,
                onCreated = navHostController::popBackStack,
                onBackClick = navHostController::popBackStack,
            )
        }
    }
}

