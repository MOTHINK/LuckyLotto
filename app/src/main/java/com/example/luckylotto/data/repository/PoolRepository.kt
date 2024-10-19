package com.example.luckylotto.data.repository

import com.example.luckylotto.data.model.Pool
import kotlinx.coroutines.flow.Flow

interface PoolRepository {
    fun getAllPoolsStream(): Flow<List<Pool>>
    fun getPoolStream(id: Int): Flow<Pool?>
    suspend fun insertPool(pool: Pool)
    suspend fun deletePool(pool: Pool)
    suspend fun updatePool(pool: Pool)
}