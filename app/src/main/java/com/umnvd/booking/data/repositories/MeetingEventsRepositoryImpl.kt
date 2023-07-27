package com.umnvd.booking.data.repositories

import android.util.Log
import com.umnvd.booking.core.data.AppDispatchers
import com.umnvd.booking.data.mappers.MeetingEventDtoMapper
import com.umnvd.booking.data.services.MeetingEventsService
import com.umnvd.booking.domain.models.MeetingEventForm
import com.umnvd.booking.domain.repositories.MeetingEventsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeetingEventsRepositoryImpl @Inject constructor(
    private val meetingEventsService: MeetingEventsService,
    private val dispatchers: AppDispatchers,
) : MeetingEventsRepository {

    override suspend fun event(uid: String) = withContext(dispatchers.io) {
        val eventDto = meetingEventsService.getEvent(uid)
        Log.d(this@MeetingEventsRepositoryImpl.javaClass.simpleName, eventDto.toString())
        return@withContext MeetingEventDtoMapper.dtoToDomain(eventDto)
    }

    override suspend fun allEvents() = withContext(dispatchers.io) {
        val eventsDtos = meetingEventsService.getEvents()
        Log.d(this@MeetingEventsRepositoryImpl.javaClass.simpleName, eventsDtos.toString())
        return@withContext eventsDtos.map(MeetingEventDtoMapper::dtoToDomain)
    }

    override suspend fun createEvent(form: MeetingEventForm) = withContext(dispatchers.io) {
        val eventDto = meetingEventsService
            .createEvent(MeetingEventDtoMapper.formDomainToDto(form))
        Log.d(this@MeetingEventsRepositoryImpl.javaClass.simpleName, eventDto.toString())
        return@withContext MeetingEventDtoMapper.dtoToDomain(eventDto)
    }

    override suspend fun editEvent(uid: String, form: MeetingEventForm) =
        withContext(dispatchers.io) {
            val eventDto = meetingEventsService
                .editEvent(uid, MeetingEventDtoMapper.formDomainToDto(form))
            Log.d(this@MeetingEventsRepositoryImpl.javaClass.simpleName, eventDto.toString())
            return@withContext MeetingEventDtoMapper.dtoToDomain(eventDto)
        }

    override suspend fun deleteEvent(uid: String) = withContext(dispatchers.io) {
        meetingEventsService.deleteEvent(uid)
    }
}