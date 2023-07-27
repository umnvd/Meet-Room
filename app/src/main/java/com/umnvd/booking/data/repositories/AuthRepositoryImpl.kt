package com.umnvd.booking.data.repositories

import android.util.Log
import com.umnvd.booking.core.data.AppDispatchers
import com.umnvd.booking.data.mappers.UserDtoMapper
import com.umnvd.booking.data.services.AuthService
import com.umnvd.booking.data.services.UsersService
import com.umnvd.booking.domain.models.User
import com.umnvd.booking.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val usersService: UsersService,
    private val dispatchers: AppDispatchers,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): User =
        withContext(dispatchers.io) {
            val userUid = authService.signIn(email, password)
            val userDto = usersService.getUser(userUid)
            Log.d(this@AuthRepositoryImpl.javaClass.simpleName, userDto.toString())
            return@withContext UserDtoMapper.dtoToDomain(userDto)
        }

    override suspend fun signOut() = withContext(dispatchers.io) {
        authService.signOut()
        Log.d(this@AuthRepositoryImpl.javaClass.simpleName, "signed out")
        return@withContext
    }

    override fun isAuthorized(): Boolean {
        return authService.currentUserUid() != null
    }
}