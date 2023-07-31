package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.rooms.models.MeetingRoomModel
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.domain.users.repositories.UsersRepository
import javax.inject.Inject

class GetUsersAndMeetingRoomsUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val roomsRepository: MeetingRoomsRepository,
) {

    suspend operator fun invoke(): Result<UsersWithRooms, AppException> {
        return try {
            val users = usersRepository.allUsers()
            val rooms = roomsRepository.allRooms()
            Result.Success(UsersWithRooms(users, rooms))
        } catch (e: AppException) {
            Result.Error(e)
        }
    }

    data class UsersWithRooms(
        val users: List<UserModel>,
        val rooms: List<MeetingRoomModel>,
    )
}