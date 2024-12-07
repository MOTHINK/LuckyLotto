package com.example.luckylotto.data.repository.pool_repository

import com.example.luckylotto.data.model.Pool
import kotlinx.coroutines.flow.Flow

interface PoolRepository {
    fun getAllPoolsStream(currentTime: Long): Flow<List<Pool>>
    fun getPool(id: Int): Flow<Pool?>
    suspend fun insertPool(pool: Pool)
    suspend fun insertPools(pools: List<Pool>)
    suspend fun deletePool(pool: Pool)
    suspend fun deletePoolById(id: String)
    suspend fun updatePool(pool: Pool)
}