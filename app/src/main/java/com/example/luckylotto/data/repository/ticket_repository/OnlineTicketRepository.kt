package com.example.luckylotto.data.repository.ticket_repository

import android.util.Log
import com.example.luckylotto.data.model.Ticket
import com.example.luckylotto.utils.randomTicketNumbers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OnlineTicketRepository {

    private val ticketCollectionName = "tickets"

    companion object { val instance: OnlineTicketRepository by lazy { OnlineTicketRepository() } }

    private suspend fun getTicket(firebaseDB: FirebaseFirestore, ticket: Ticket): Boolean {
        return try {
            firebaseDB.collection(ticketCollectionName).whereEqualTo("poolId", ticket.poolId)
                .whereEqualTo("ticketNumber", ticket.ticketNumber)
                .get()
                .addOnSuccessListener { documentReference -> Log.d("FIRESTORE_GET_TICKET", "DocumentSnapshot got with ID: $documentReference") }
                .addOnFailureListener { e -> Log.w("FIRESTORE_GET_TICKET", "Error getting document", e) }
                .await().isEmpty
        } catch (_: Exception) {
            false
        }
    }

    suspend fun insertTicket(firebaseDB: FirebaseFirestore, ticket: Ticket): Ticket? {
        val ticketNumber = randomTicketNumbers()
        return if(this.getTicket(firebaseDB,ticket.copy(ticketNumber = ticketNumber))) {
            try {
                firebaseDB.collection(ticketCollectionName).document(ticket.ticketId)
                    .set(ticket.copy(ticketNumber = ticketNumber))
                    .addOnSuccessListener { documentReference -> Log.d("FIRESTORE_INSERT_TICKET", "DocumentSnapshot added with ID: $documentReference") }
                    .addOnFailureListener { e -> Log.w("FIRESTORE_INSERT_TICKET", "Error adding document", e) }
                    .await()
                ticket.copy(ticketNumber = ticketNumber)
            } catch (_: Exception) {
                null
            }
        } else {
            this.insertTicket(firebaseDB,ticket)
        }
    }

    suspend fun updateTicketPrizeClaim(firebaseDB: FirebaseFirestore, ticket: Ticket): Boolean {
        return try {
            firebaseDB.collection(ticketCollectionName).document(ticket.ticketId)
                .update("prizeClaimed", true)
                .addOnSuccessListener {
                    Log.d("FIRESTORE_UPDATE_TICKET", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e -> Log.w("FIRESTORE_UPDATE_TICKET", "Error updating ticket", e)
                }.await()
            true
        } catch (_: Exception) {
            false
        }
    }

}