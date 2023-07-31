package com.umnvd.booking.domain.events.usecases

import com.umnvd.booking.core.domain.models.Result
import com.umnvd.booking.domain.AppException
import com.umnvd.booking.domain.events.models.MeetingEventModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import com.umnvd.booking.domain.users.repositories.UsersRepository
import javax.inject.Inject

class GetUserMeetingEventListUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val meetingEventsRepository: MeetingEventsRepository,
) {

    suspend operator fun invoke(): Result<List<MeetingEventModel>, AppException> {
        return try {
            val user = usersRepository.currentUser()
            val events = meetingEventsRepository.userEvents(user)
            Result.Success(events)
        } catch (e: AppException) {
            Result.Error(e)
        }
    }
}