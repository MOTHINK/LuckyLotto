package com.example.luckylotto.data.model

import androidx.room.PrimaryKey

data class Ticket(
    @PrimaryKey(autoGenerate = false)
    val ticketNumber: String = "",
    val poolId: String = "",
    val closeTime: Long = 0L,
    val ticketsBought: Int = 0,
    val poolImage: String = "",
    val privatePool: Boolean = false
)