package com.example.luckylotto.data.model

data class Pool(
    val poolId: String,
    val maxPrize: Double,
    val maxPlayers: Int,
    val currentPlayers: Int,
    val closeTime: Long
)
