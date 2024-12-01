package com.example.luckylotto.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ticket: Ticket)
    @Query("DELETE FROM tickets WHERE ticketId = :ticketId;")
    suspend fun deleteById(ticketId: String)
    @Query("SELECT * from tickets WHERE userId = :userId ORDER BY closeTime DESC;")
    fun getAllMyTickets(userId: String): Flow<List<Ticket>>

}