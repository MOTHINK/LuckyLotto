package com.example.luckylotto.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.repository.PoolRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val poolRepository: PoolRepository) : ViewModel() {

    val imageList: List<String> = listOf(
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-z1hd74qvl6qjz2r7.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-vrdvfljmxy0kg095.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-o0v66q06jh6yp9bc.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-heiha21lpwl9drp0.webp",
        "https://wallpapers.com/images/high/light-colour-pictures-shw0kdp5z6ucula7.webp"
    )

    private val _user: MutableStateFlow<FirebaseUser?> = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    private val _pools: MutableStateFlow<List<Pool>> = MutableStateFlow<List<Pool>>(emptyList())
    val pools: StateFlow<List<Pool>> = _pools

    private val _poolSearchText: MutableStateFlow<String> = MutableStateFlow<String>("")
    val poolSearchText: StateFlow<String> = _poolSearchText

    init {
        viewModelScope.launch {
            getAllPoolsFromDatabase()
        }
    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        _user.value = firebaseUser
    }

    fun setPoolSearchText(searchText: String) {
        _poolSearchText.update { searchText }
    }
    private suspend fun getAllPoolsFromDatabase() {
        poolRepository.getAllPoolsStream().collect { pools ->
            _pools.value = pools
        }
    }

    suspend fun createNewPoolTest(pool: Pool) {
        poolRepository.insertPool(pool)
    }

}