package com.umnvd.booking.data.events.repositories

import com.umnvd.booking.data.events.mappers.MeetingEventRemoteModelMapper
import com.umnvd.booking.data.events.services.MeetingEventsService
import com.umnvd.booking.di.IoDispatcher
import com.umnvd.booking.domain.events.models.MeetingEventFormModel
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import com.umnvd.booking.domain.users.models.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeetingEventsRepositoryImpl @Inject constructor(
    private val meetingEventsService: MeetingEventsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MeetingEventsRepository {

    override suspend fun event(uid: String) = withContext(ioDispatcher) {
        val eventDto = meetingEventsService.getEvent(uid)
        return@withContext MeetingEventRemoteModelMapper.dtoToDomain(eventDto)
    }

    override suspend fun allEvents() = withContext(ioDispatcher) {
        val eventsDtos = meetingEventsService.getEvents()
        return@withContext eventsDtos.map(MeetingEventRemoteModelMapper::dtoToDomain)
    }

    override suspend fun createEvent(form: MeetingEventFormModel) = withContext(ioDispatcher) {
        val eventDto = meetingEventsService
            .createEvent(MeetingEventRemoteModelMapper.formDomainToDto(form))
        return@withContext MeetingEventRemoteModelMapper.dtoToDomain(eventDto)
    }

    override suspend fun editEvent(uid: String, form: MeetingEventFormModel) =
        withContext(ioDispatcher) {
            val eventDto = meetingEventsService
                .editEvent(uid, MeetingEventRemoteModelMapper.formDomainToDto(form))
            return@withContext MeetingEventRemoteModelMapper.dtoToDomain(eventDto)
        }

    override suspend fun deleteEvent(uid: String) = withContext(ioDispatcher) {
        meetingEventsService.deleteEvent(uid)
    }

    override suspend fun userEvents(user: UserModel) = withContext(ioDispatcher) {
        allEvents().filter { it.participants.contains(user) }
    }
}