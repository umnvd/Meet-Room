package com.umnvd.booking.domain.repositories

import com.umnvd.booking.domain.models.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): User

    suspend fun signOut()
}