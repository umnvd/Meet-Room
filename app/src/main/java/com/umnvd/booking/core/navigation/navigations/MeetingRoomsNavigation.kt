import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.umnvd.booking.presentation.rooms.creation.MeetingRoomCreationScreen
import com.umnvd.booking.presentation.rooms.list.MeetingRoomListScreen
import com.umnvd.booking.presentation.rooms.room.MeetingRoomScreen

const val ROOMS_GRAPH_ROUTE = "rooms_graph"
const val ROOM_ROUTE_UID_KEY = "uid"

private const val ROOM_LIST_ROUTE = "rooms"
private const val ROOM_ROUTE_BASE = "room/"
private const val ROOM_ROUTE = "$ROOM_ROUTE_BASE{$ROOM_ROUTE_UID_KEY}"
private const val CREATE_ROOM_ROUTE = "create_room"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.meetingRoomsGraph(
    navController: NavController,
) {
    navigation(startDestination = ROOM_LIST_ROUTE, route = ROOMS_GRAPH_ROUTE) {
        composable(route = ROOM_LIST_ROUTE) {
            MeetingRoomListScreen(
                onRoomClick = { navController.navigate("$ROOM_ROUTE_BASE${it.uid}") },
                onCreateClick = { navController.navigate(CREATE_ROOM_ROUTE) },
            )
        }
        composable(
            route = ROOM_ROUTE,
            arguments = listOf(navArgument(ROOM_ROUTE_UID_KEY) { type = NavType.StringType })
        ) {
            val uid = it.arguments?.getString(ROOM_ROUTE_UID_KEY)
                ?: throw IllegalStateException("Meeting room UID not specified.")
            Log.d("Navigation", it.destination.toString())
            MeetingRoomScreen(
                onEdited = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
        composable(route = CREATE_ROOM_ROUTE) {
            MeetingRoomCreationScreen(
                onCreated = navController::popBackStack,
                onBackClick = navController::popBackStack,
            )
        }
    }
}

