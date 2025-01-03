package com.example.luckylotto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pools")
data class Pool(
    @PrimaryKey(autoGenerate = false)
    val poolId: String = "",
    val userId: String = "",
    val winningNumber: String = "000000",
    val maxPrize: Double = 0.0,
    val maxTickets: Int = 0,
    val ticketsBought: Int = 0,
    val startTime: Long = 0L,
    val closeTime: Long = 0L,
    val poolImage: String = "",
    val isPrivate: Boolean = false
)
