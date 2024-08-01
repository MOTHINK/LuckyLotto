package com.example.luckylotto.data.core.firebase

import android.util.Log
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

    fun initializeFirebaseAuth() {
        this.auth = Firebase.auth
    }

    fun getFirebaseCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInFirebaseAuthentication(idToken: String, navigatingTo: () -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        this.auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase_Auth_Success", "signInWithCredential:success")
                    navigatingTo()
                } else {
                    Log.w("Firebase_Auth_Fail", "signInWithCredential:failure", task.exception)
                }
            }
    }

    fun signOutFirebaseAuthentication(navigatingTo: () -> Unit) {
        this.auth.signOut()
        navigatingTo()
    }

}