package com.example.luckylotto.data.repository.wallet_repository

import com.example.luckylotto.data.dao.WalletDao
import com.example.luckylotto.data.model.Wallet
import kotlinx.coroutines.flow.Flow

class OfflineWalletRepository(private val walletDao: WalletDao): WalletRepository {
    override suspend fun insert(wallet: Wallet) = walletDao.insert(wallet)
    override fun getWallet(userId: String): Flow<Wallet?> = walletDao.getWalletById(userId)
    override suspend fun deleteWallet(wallet: Wallet) = walletDao.delete(wallet)
    override suspend fun updateWalletIncrementingCoinsById(userId: String, coins: Int) = walletDao.updateWalletIncrementingCoinsById(userId,coins)
    override suspend fun updateWalletDecrementingThreeCoinsById(userId: String, coins: Int) = walletDao.updateWalletDecrementingThreeCoinsById(userId,coins)

}