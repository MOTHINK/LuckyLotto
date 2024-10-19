package com.example.luckylotto.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.repository.PoolRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toCollection

class MainViewModel(private val poolRepository: PoolRepository) : ViewModel() {

    private val _user: MutableStateFlow<FirebaseUser?> = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    private val _pools: MutableStateFlow<List<Pool>> = MutableStateFlow<List<Pool>>(emptyList())
    val pools: StateFlow<List<Pool>> = _pools

    init {

    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        _user.value = firebaseUser
    }

    suspend fun getAllPoolsFromDatabase() {
        poolRepository.getAllPoolsStream().collect { pools ->
            _pools.value = pools
        }
    }


}