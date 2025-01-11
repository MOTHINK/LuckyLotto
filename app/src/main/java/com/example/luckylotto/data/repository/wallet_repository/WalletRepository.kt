package com.example.luckylotto.data.repository.wallet_repository

import com.example.luckylotto.data.model.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun insert(wallet: Wallet)
    fun getWallet(userId: String): Flow<Wallet?>
    suspend fun deleteWallet(wallet: Wallet)
    suspend fun updateWalletIncrementingCoinsById(userId: String, coins: Int)
    suspend fun updateWalletDecrementingThreeCoinsById(userId: String, coins: Int)

}