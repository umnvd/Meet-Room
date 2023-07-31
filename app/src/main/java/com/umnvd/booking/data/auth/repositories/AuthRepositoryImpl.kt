package com.umnvd.booking.data.auth.repositories

import com.umnvd.booking.data.auth.services.AuthService
import com.umnvd.booking.data.users.mappers.UserRemoteModelMapper
import com.umnvd.booking.data.users.services.UsersService
import com.umnvd.booking.di.IoDispatcher
import com.umnvd.booking.domain.auth.repositories.AuthRepository
import com.umnvd.booking.domain.users.models.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val usersService: UsersService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): UserModel =
        withContext(ioDispatcher) {
            val userUid = authService.signIn(email, password)
            val userDto = usersService.getUser(userUid)
            return@withContext UserRemoteModelMapper.dtoToDomain(userDto)
        }

    override suspend fun signOut() = withContext(ioDispatcher) {
        authService.signOut()
        return@withContext
    }

    override fun isAuthorized(): Boolean {
        return authService.currentUserUid() != null
    }
}