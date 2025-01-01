package com.example.luckylotto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = false)
    val ticketId: String = "",
    val firebaseDocumentReferenceId: String = "",
    val ticketNumber: String = "",
    val poolId: String = "",
    val userId: String = "",
    val closeTime: Long = 0L,
    val ticketsBought: Int = 0,
    val maxTickets: Int = 0,
    val winningNumber: String = "000000",
    val poolImage: String = "",
    val privatePool: Boolean = false,
    val prizeClaimed: Boolean = false,
)