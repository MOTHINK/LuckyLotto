package com.example.luckylotto.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.luckylotto.data.model.Pool
import kotlinx.coroutines.flow.Flow

@Dao
interface PoolDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pool: Pool)
    @Update
    suspend fun update(pool: Pool)
    @Delete
    suspend fun delete(pool: Pool)
    @Query("SELECT * from pools WHERE poolId = :id")
    fun getPool(id: Int): Flow<Pool>
    @Query("SELECT * from pools WHERE closeTime > :currentTime AND isPrivate = 0")
    fun getAllPools(currentTime: Long): Flow<List<Pool>>
    @Query("DELETE from pools WHERE poolId = :id")
    fun deletePoolById(id: String)

}