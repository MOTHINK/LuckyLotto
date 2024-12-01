package com.example.luckylotto.data.core.firebase

import android.util.Log
import com.example.luckylotto.ui.viewmodel.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class FirebaseAuthentication {

    private lateinit var auth: FirebaseAuth

    companion object {
        val instance: FirebaseAuthentication by lazy { FirebaseAuthentication() }
    }

    suspend fun initializeFirebaseAuth(): FirebaseAuth {
        this.auth = Firebase.auth
        return this.auth
    }

    fun getFirebaseCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInFirebaseAuthentication(
        idToken: String,
        navigatingTo: () -> Unit,
        mainViewModel: MainViewModel
    ) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        this.auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase_Auth_Success", "signInWithCredential:success")
                    mainViewModel.setFirebaseUser(task.result.user)
                    navigatingTo()
                } else {
                    Log.w("Firebase_Auth_Fail", "signInWithCredential:failure", task.exception)
                }
            }
    }

    fun signOutFirebaseAuthentication(mainViewModel: MainViewModel, navigatingTo: () -> Unit) {
        this.auth.signOut()
        mainViewModel.setFirebaseUser(null)
        navigatingTo()
    }

}