package com.umnvd.booking.data.services

import com.google.firebase.auth.FirebaseAuth
import com.umnvd.booking.domain.errors.AuthException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signIn(email: String, password: String): String {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return result.user!!.uid
        } catch (e: Exception) {
            throw AuthException
        }
    }

    fun signOut() = firebaseAuth.signOut()
}