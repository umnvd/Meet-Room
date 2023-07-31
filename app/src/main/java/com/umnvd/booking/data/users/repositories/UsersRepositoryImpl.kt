package com.umnvd.booking.data.users.repositories

import com.umnvd.booking.data.auth.services.AuthService
import com.umnvd.booking.data.users.mappers.UserRemoteModelMapper
import com.umnvd.booking.data.users.services.UsersService
import com.umnvd.booking.di.IoDispatcher
import com.umnvd.booking.domain.UnauthorizedException
import com.umnvd.booking.domain.users.models.UserModel
import com.umnvd.booking.domain.users.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
    private val authService: AuthService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UsersRepository {

    override suspend fun currentUser(): UserModel {
        val currentUserUid = authService.currentUserUid() ?: throw UnauthorizedException()
        return user(currentUserUid)
    }

    override suspend fun user(uid: String): UserModel = withContext(ioDispatcher) {
        val userDto = usersService.getUser(uid)
        return@withContext UserRemoteModelMapper.dtoToDomain(userDto)
    }

    override suspend fun allUsers(): List<UserModel> = withContext(ioDispatcher) {
        val userDtos = usersService.getUsers()
        return@withContext userDtos.map(UserRemoteModelMapper::dtoToDomain)
    }
}