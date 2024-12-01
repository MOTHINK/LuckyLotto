package com.example.luckylotto.data.database

import android.content.Context
import com.example.luckylotto.data.repository.pool_repository.OfflinePoolsRepository
import com.example.luckylotto.data.repository.pool_repository.PoolRepository
import com.example.luckylotto.data.repository.ticket_repository.OfflineTicketRepository
import com.example.luckylotto.data.repository.ticket_repository.TicketRepository

interface AppContainer {
    val poolRepository: PoolRepository
    val ticketRepository: TicketRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val poolRepository: PoolRepository by lazy {
        OfflinePoolsRepository(LuckyLottoDatabase.getDatabase(context).poolDao())
    }
    override val ticketRepository: TicketRepository by lazy {
        OfflineTicketRepository(LuckyLottoDatabase.getDatabase(context).ticketDao())
    }
}