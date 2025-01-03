package com.example.luckylotto.data.repository.ticket_repository

import com.example.luckylotto.data.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketRepository {

    fun getAllTickets(userId: String): Flow<List<Ticket>>
    suspend fun deleteTicketById(ticketId: String)
    suspend fun insertTicket(ticket: Ticket)
    suspend fun updateTicket(ticket: Ticket)
    suspend fun updateBoughtTicketsById(poolId: String, ticketsBought: Int)

}