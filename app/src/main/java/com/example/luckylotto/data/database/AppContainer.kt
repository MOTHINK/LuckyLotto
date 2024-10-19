package com.example.luckylotto.data.database

import android.content.Context
import com.example.luckylotto.data.repository.OfflinePoolsRepository
import com.example.luckylotto.data.repository.PoolRepository

interface AppContainer {
    val poolRepository: PoolRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val poolRepository: PoolRepository by lazy {
        OfflinePoolsRepository(LuckyLottoDatabase.getDatabase(context).poolDao())
    }
}