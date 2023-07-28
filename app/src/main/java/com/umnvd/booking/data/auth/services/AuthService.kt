package com.umnvd.booking.data.auth.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.umnvd.booking.data.utils.withFirebaseExceptionMapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signIn(email: String, password: String): String = withFirebaseExceptionMapper {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user!!.uid
    }

    suspend fun signOut() {
        return suspendCoroutine { continuation ->
            Log.d("SIGN_OUT", "begin")
            val authStateListener = object : AuthStateListener {
                override fun onAuthStateChanged(state: FirebaseAuth) {
                    Log.d("SIGN_OUT", "inside onAuthStateChanged")
                    Log.d("SIGN_OUT", "state: ${state.currentUser}")
                    if (state.currentUser == null) {
                        Log.d("SIGN_OUT", "remove itself")
                        firebaseAuth.removeAuthStateListener(this)
                        Log.d("SIGN_OUT", "resume continuation")
                        continuation.resume(Unit)
                    }
                }

            }
            Log.d("SIGN_OUT", "call addAuthStateListener")
            firebaseAuth.addAuthStateListener(authStateListener)
            Log.d("SIGN_OUT", "call signOut")
            firebaseAuth.signOut()
            Log.d("SIGN_OUT", "end")
        }
    }

    fun currentUserUid(): String? = firebaseAuth.currentUser?.uid
}