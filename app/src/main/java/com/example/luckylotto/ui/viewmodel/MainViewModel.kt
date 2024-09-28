package com.example.luckylotto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _user: MutableStateFlow<FirebaseUser?> = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    init {

    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        _user.value = firebaseUser
    }

}