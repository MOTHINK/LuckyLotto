package com.example.luckylotto.data.repository.ticket_repository

import com.example.luckylotto.data.dao.TicketDao
import com.example.luckylotto.data.model.Ticket
import kotlinx.coroutines.flow.Flow

class OfflineTicketRepository(private val ticketDao: TicketDao) : TicketRepository {
    override fun getAllTickets(userId: String): Flow<List<Ticket>> = ticketDao.getAllMyTickets(userId)
    override suspend fun deleteTicketById(ticketId: String) = ticketDao.deleteById(ticketId)
    override suspend fun insertTicket(ticket: Ticket) = ticketDao.insert(ticket)
    override suspend fun updateTicket(ticket: Ticket) = ticketDao.updateTicket(ticket)
}