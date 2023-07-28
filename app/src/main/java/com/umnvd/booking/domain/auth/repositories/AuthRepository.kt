package com.umnvd.booking.domain.auth.repositories

import com.umnvd.booking.domain.users.models.UserModel

interface AuthRepository {

    suspend fun signIn(email: String, password: String): UserModel

    suspend fun signOut()

    fun isAuthorized(): Boolean
}