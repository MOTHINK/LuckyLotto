package com.example.luckylotto.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.PrizeRequest
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.data.model.Wallet
import com.example.luckylotto.data.repository.pool_repository.OnlinePoolsRepository
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.prize_request_repository.OnlinePrizeRequestRepository
import com.example.luckylotto.data.repository.ticket_repository.OnlineTicketRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository
import com.example.luckylotto.data.repository.wallet_repository.WalletRepository
import com.example.luckylotto.utils.randomTicketNumbers
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(private val poolRepository: PoolRepository,private val ticketRepository: TicketRepository, private val walletRepository: WalletRepository) : ViewModel() {

    val firebaseDB = Firebase.firestore

    private val _snackBarMessage: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val snackBarMessage: StateFlow<Int> = _snackBarMessage

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

    private val _wallet: MutableStateFlow<Wallet?> = MutableStateFlow<Wallet?>(null)
    val wallet: StateFlow<Wallet?> = _wallet

    init {
        viewModelScope.launch { FirebaseAuthentication.instance.initializeFirebaseAuth() }
        viewModelScope.launch { setFirebaseUser(FirebaseAuthentication.instance.getFirebaseCurrentUser()) }
        if (user.value != null) {
            viewModelScope.launch { getAllTicketsFromDatabase() }
            viewModelScope.launch { getAllPoolsFromDatabase() }
            viewModelScope.launch { getWallet() }
        }
    }

    private suspend fun getWallet() {
        walletRepository.getWallet(FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid).collect {
            _wallet.value = it
        }
    }

    fun createWallet(wallet: Wallet) {
        viewModelScope.launch {
            walletRepository.insert(wallet)
        }
    }

    suspend fun incrementCoin() {
        walletRepository.updateWalletIncrementingCoinsById(FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid, wallet.value!!.coins)
    }

    fun setNavBarIndex(index: Int) {
        _navBarIndex.value = index
    }

    fun setSnackBarMessage(n: Int) {
        _snackBarMessage.value = n
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

    private fun insertAllPoolsIntoDatabase(pools: List<Pool>) {
        viewModelScope.launch {
            poolRepository.insertPools(pools)
        }
    }

    fun getAllPoolsFromFirebaseDatabase(firebaseDB: FirebaseFirestore) {
        OnlinePoolsRepository.instance.getAllPoolsStream(firebaseDB,System.currentTimeMillis()) {
            _pools.value = it
            insertAllPoolsIntoDatabase(it)
        }
    }

    private suspend fun getAllTicketsFromDatabase() {
        ticketRepository.getAllTickets(FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid).collect { tickets ->
            _tickets.value = tickets
        }
    }

    suspend fun createPoolAndGetTicket(firebaseDB: FirebaseFirestore, maxTickets: Int, closeTime: Long, poolImage: String, isPrivate: Boolean): Boolean {
        Log.d("Checking1234", "Executing createPoolAndGetTicket function")
        val pool = Pool(
            poolId = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.displayName.toString()+"-"+System.currentTimeMillis(),
            userId = FirebaseAuthentication.instance.getFirebaseCurrentUser()?.uid.toString(),
            maxTickets = maxTickets,
            ticketsBought = 0,
            closeTime = System.currentTimeMillis()+closeTime,
            startTime = System.currentTimeMillis(),
            poolImage = poolImage,
            isPrivate = isPrivate
        )
        return createNewPool(firebaseDB,pool) && createNewTicket(firebaseDB,pool)
    }

    private suspend fun createNewPool(firebaseDB: FirebaseFirestore, pool: Pool): Boolean {
        Log.d("Checking1234", "Executing createNewPool")
        var inserted = false
        if(OnlinePoolsRepository.instance.insertPool(firebaseDB,pool)) {
            inserted = try {
                poolRepository.insertPool(pool)
                _pools.value += pool
                true
            } catch (_: Exception) {
                false
            }
        }
        return inserted
    }

    suspend fun createNewTicket(firebaseDB: FirebaseFirestore, pool: Pool): Boolean {
        Log.d("Checking1234", "Executing createNewTicket")
        var inserted = false
        var incrementedLocal = false
        var incrementedOnline = false

        val ticket = Ticket(
            ticketId = UUID.randomUUID().toString(),
            ticketNumber = randomTicketNumbers(),
            poolId = pool.poolId,
            userId = FirebaseAuthentication.instance.getFirebaseCurrentUser()!!.uid,
            closeTime = pool.startTime+(pool.closeTime-pool.startTime),
            ticketsBought = pool.ticketsBought + 1,
            maxTickets = pool.maxTickets,
            poolImage = pool.poolImage,
            privatePool = pool.isPrivate
        )

        if(OnlineTicketRepository.instance.insertTicket(firebaseDB,ticket)) {
            inserted = try {
                ticketRepository.insertTicket(ticket)
                true
            } catch (_: Exception) {
                false
            }

            incrementedOnline = OnlinePoolsRepository.instance.incrementBoughtTickets(firebaseDB,pool)

            incrementedLocal = try {
                poolRepository.updatePool(pool.copy(ticketsBought = pool.ticketsBought + 1))
                ticketRepository.updateBoughtTicketsById(pool.poolId,pool.ticketsBought + 1)
                true
            } catch (_: Exception) {
                false
            }
        }
        Log.d("Checking1234", "inserted: $inserted - incrementedLocal: $incrementedLocal - incrementedOnline: $incrementedOnline")
        return inserted && incrementedLocal && incrementedOnline
    }

    private fun updateTicketOnDatabase(ticket: Ticket) {
        viewModelScope.launch {
            ticketRepository.updateTicket(ticket)
        }
    }

    fun deleteTicketById(ticketId: String): Boolean {
        return try {
            viewModelScope.launch { ticketRepository.deleteTicketById(ticketId) }
            true
        } catch (_: Exception) {
            false
        }
    }

    private suspend fun updatePoolById(poolId: String) {
        OnlinePoolsRepository.instance.getPool(firebaseDB, poolId) {
            _pools.value = _pools.value.toMutableList().map { pool ->
                if (pool.poolId == it.poolId) {
                    updatePoolOnDatabase(it)
                    it
                } else {
                    pool
                }
            }
            updateTicketById(poolId,it.ticketsBought, it.winningNumber)
        }
    }
    private fun updateTicketById(poolId: String, ticketBought: Int, winningNumber: String) {
        _tickets.value = _tickets.value.toMutableList().map { ticket ->
            if(ticket.poolId == poolId) {
                val updatedTicket = ticket.copy(ticketsBought = ticketBought, winningNumber = winningNumber)
                updateTicketOnDatabase(updatedTicket)
                updatedTicket
            } else {
                ticket
            }
        }
    }

    private fun updatePoolOnDatabase(pool: Pool) {
        viewModelScope.launch { poolRepository.updatePool(pool) }
    }

    suspend fun updateTicketAndPoolByPoolId(poolId: String) {
        updatePoolById(poolId)
    }

    fun sharePool(poolId: String) {

    }

    suspend fun claimingPrize(ticket: Ticket): Boolean {
        return if(
            OnlinePrizeRequestRepository.instance.prizeRequest(firebaseDB, PrizeRequest(userId = ticket.userId, ticketId = ticket.ticketId, poolId = ticket.poolId)) &&
            OnlineTicketRepository.instance.updateTicketPrizeClaim(firebaseDB,ticket)
        ) {
            updateTicketOnDatabase(ticket.copy(prizeClaimed = true))
            true
        } else {
            false
        }
    }

}