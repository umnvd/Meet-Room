package com.umnvd.booking.di

import com.umnvd.booking.data.auth.repositories.AuthRepositoryImpl
import com.umnvd.booking.data.events.repositories.MeetingEventsRepositoryImpl
import com.umnvd.booking.data.rooms.repositories.MeetingRoomsRepositoryImpl
import com.umnvd.booking.data.users.repositories.UsersRepositoryImpl
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import com.umnvd.booking.domain.events.repositories.MeetingEventsRepository
import com.umnvd.booking.domain.rooms.repositories.MeetingRoomsRepository
import com.umnvd.booking.domain.users.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    fun bindMeetingRoomsRepository(
        meetingRoomsRepositoryImpl: MeetingRoomsRepositoryImpl
    ): MeetingRoomsRepository

    @Binds
    fun bindMeetingEventsRepository(
        meetingEventsRepositoryImpl: MeetingEventsRepositoryImpl
    ): MeetingEventsRepository
}