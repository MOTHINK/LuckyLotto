package com.example.luckylotto.data.repository

import com.example.luckylotto.data.dao.PoolDao
import com.example.luckylotto.data.model.Pool
import kotlinx.coroutines.flow.Flow

class OfflinePoolsRepository(private val poolDao: PoolDao) : PoolRepository {
    override fun getAllPoolsStream(): Flow<List<Pool>> = poolDao.getAllPools()
    override fun getPoolStream(id: Int): Flow<Pool?> = poolDao.getPool(id)
    override suspend fun insertPool(pool: Pool) = poolDao.insert(pool)
    override suspend fun deletePool(pool: Pool) = poolDao.delete(pool)
    override suspend fun updatePool(pool: Pool) = poolDao.update(pool)

}