package com.example.luckylotto.data.database

import android.content.Context
import com.example.luckylotto.data.repository.pool_repository.OfflinePoolsRepository
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.ticket_repository.OfflineTicketRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository
import com.example.luckylotto.data.repository.wallet_repository.OfflineWalletRepository
import com.example.luckylotto.data.repository.wallet_repository.WalletRepository

interface AppContainer {
    val poolRepository: PoolRepository
    val ticketRepository: TicketRepository
    val walletRepository: WalletRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val poolRepository: PoolRepository by lazy {
        OfflinePoolsRepository(LuckyLottoDatabase.getDatabase(context).poolDao())
    }
    override val ticketRepository: TicketRepository by lazy {
        OfflineTicketRepository(LuckyLottoDatabase.getDatabase(context).ticketDao())
    }
    override val walletRepository: WalletRepository by lazy {
        OfflineWalletRepository(LuckyLottoDatabase.getDatabase(context).walletDao())
    }
}