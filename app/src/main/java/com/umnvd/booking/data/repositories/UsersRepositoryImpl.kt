package com.umnvd.booking.data.repositories

import android.util.Log
import com.umnvd.booking.core.data.AppDispatchers
import com.umnvd.booking.data.mappers.UserDtoMapper
import com.umnvd.booking.data.services.AuthService
import com.umnvd.booking.data.services.UsersService
import com.umnvd.booking.domain.errors.UnauthorizedException
import com.umnvd.booking.domain.models.User
import com.umnvd.booking.domain.repositories.UsersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersService: UsersService,
    private val authService: AuthService,
    private val dispatchers: AppDispatchers,
) : UsersRepository {

    override suspend fun currentUser(): User {
        val currentUserUid = authService.currentUserUid() ?: throw UnauthorizedException()
        return user(currentUserUid)
    }

    override suspend fun user(uid: String): User = withContext(dispatchers.io) {
        val userDto = usersService.getUser(uid)
        Log.d(this@UsersRepositoryImpl.javaClass.simpleName, userDto.toString())
        return@withContext UserDtoMapper.dtoToDomain(userDto)
    }

    override suspend fun allUsers(): List<User> = withContext(dispatchers.io) {
        val userDtos = usersService.getUsers()
        Log.d(this@UsersRepositoryImpl.javaClass.simpleName, userDtos.toString())
        return@withContext userDtos.map(UserDtoMapper::dtoToDomain)
    }
}