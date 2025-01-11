package com.example.luckylotto.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.luckylotto.data.model.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wallet: Wallet)
    @Query("SELECT * from wallets WHERE userId = :userId limit 1;")
    fun getWalletById(userId: String): Flow<Wallet?>
    @Delete
    suspend fun delete(wallet: Wallet)
    @Query("UPDATE wallets SET coins = :coins + 1 WHERE userId = :userId")
    suspend fun updateWalletIncrementingCoinsById(userId: String, coins: Int)

    @Query("UPDATE wallets SET coins = :coins - 3 WHERE userId = :userId")
    suspend fun updateWalletDecrementingThreeCoinsById(userId: String, coins: Int)
}