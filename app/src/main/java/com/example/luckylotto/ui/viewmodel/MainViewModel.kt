package com.example.luckylotto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.data.repository.pool_repository.OnlinePoolsRepository
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(private val poolRepository: PoolRepository,private val ticketRepository: TicketRepository) : ViewModel() {

    val firebaseDB = Firebase.firestore

    private val _snackBarMessage: MutableStateFlow<String> = MutableStateFlow<String>("")
    val snackBarMessage: StateFlow<String> = _snackBarMessage

    private val _navBarIndex: MutableStateFlow<Int> = MutableStateFlow<Int>(2)
    val navBarIndex: StateFlow<Int> = _navBarIndex

    val maxTicketValues = listOf(50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000)
    val maxTimeValues = listOf(1,6,12,24,48,72,168,336,672)

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

    private val _tickets: MutableStateFlow<List<Ticket>> = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets: StateFlow<List<Ticket>> = _tickets

    init {
        viewModelScope.launch {
            FirebaseAuthentication.instance.initializeFirebaseAuth()
        }
        viewModelScope.launch {
            setFirebaseUser(FirebaseAuthentication.instance.getFirebaseCurrentUser())
        }
        viewModelScope.launch {
            getAllTicketsFromDatabase()
        }
        viewModelScope.launch {
            OnlinePoolsRepository.instance.getAllPoolsStream(firebaseDB,System.currentTimeMillis()) { _pools.value = it }
            getAllPoolsFromDatabase()
        }
    }

    fun setNavBarIndex(index: Int) {
        _navBarIndex.value = index
    }

    fun setSnackBarMessage(message: String) {
        _snackBarMessage.value = message
    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        if(user.value == null) {
            _user.value = firebaseUser
        }

    }

    fun setPoolSearchText(searchText: String) {
        _poolSearchText.update { searchText }
    }
    private suspend fun getAllPoolsFromDatabase() {
        poolRepository.getAllPoolsStream(System.currentTimeMillis()).collect { pools ->
            _pools.value = pools
        }
    }

    suspend fun getAllPoolsFromFirebaseDatabase() {

    }

    private suspend fun getAllTicketsFromDatabase() {
        ticketRepository.getAllTickets(FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid).collect { tickets ->
            _tickets.value = tickets
        }
    }

    suspend fun createPoolAndGetTicket(maxTickets: Int, closeTime: Long, poolImage: String, isPrivate: Boolean): Boolean {
        val pool = Pool(
            poolId = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.displayName.toString()+"-"+System.currentTimeMillis(),
            userId = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.uid.toString(),
            maxTickets = maxTickets,
            closeTime = System.currentTimeMillis()+closeTime,
            startTime = System.currentTimeMillis(),
            poolImage = poolImage,
            isPrivate = isPrivate
        )
        return createNewPool(pool) && createNewTicket(pool)
    }

    private suspend fun createNewPool(pool: Pool): Boolean {
        return try {
            poolRepository.insertPool(pool)
            true
        } catch (_: Exception) {
            false
        }
    }

    private suspend fun createNewTicket(pool: Pool): Boolean {
        return try {
            ticketRepository.insertTicket(
                Ticket(
                    UUID.randomUUID().toString(),
                    123456.toString(),
                    pool.poolId,
                    FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid,
                    pool.startTime+(pool.closeTime-pool.startTime),
                    pool.ticketsBought,
                    pool.maxTickets,
                    pool.poolImage,
                    pool.isPrivate
                )
            )
            true
        } catch (_: Exception) {
            false
        }
    }

    fun deleteTicketById(ticketId: String): Boolean {
        return try {
            viewModelScope.launch {
                ticketRepository.deleteTicketById(ticketId)
            }
            true
        } catch (_: Exception) {
            false
        }
    }

}