package com.umnvd.booking.data.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.umnvd.booking.core.data.AppDispatchers
import com.umnvd.booking.data.services.AuthService
import com.umnvd.booking.domain.models.User
import com.umnvd.booking.domain.repositories.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dispatchers: AppDispatchers,
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): User {
        try {
            val userUid = authService.signIn(email, password)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signOut() = withContext(dispatchers.io) {
        Firebase.auth.signOut()
    }
}