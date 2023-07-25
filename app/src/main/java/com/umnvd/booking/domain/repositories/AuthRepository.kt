package com.umnvd.booking.domain.repositories

interface AuthRepository {
    suspend fun signIn(login: String, password: String): String

    suspend fun signOut()
}