package com.example.luckylotto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = false)
    val userId: String = "",
    val coins: Int = 0
)
