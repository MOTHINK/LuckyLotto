package com.example.luckylotto.data.repository.ticket_repository

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore

class OnlineTicketRepository {

    private val ticketCollectionName = "tickets"

    companion object {
        val instance: OnlineTicketRepository by lazy { OnlineTicketRepository() }
    }

    fun insertTicket(firebaseDB: FirebaseFirestore, ticket: Ticket) {
        firebaseDB.collection(ticketCollectionName)
            .add(ticket)
            .addOnSuccessListener { documentReference ->
                Log.d("FIRESTORE_INSERT_TICKET", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FIRESTORE_INSERT_TICKET", "Error adding document", e)
            }
    }

    fun updateTicket(firebaseDB: FirebaseFirestore, ticket: Ticket) {
        firebaseDB.collection(ticketCollectionName).whereEqualTo("ticketId", ticket.ticketId)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    firebaseDB.collection(ticketCollectionName).document(result.documents[0].id)
                        .update("prizeClaimed", true)
                        .addOnSuccessListener { Log.d("FIRESTORE_UPDATE_TICKET", "DocumentSnapshot successfully updated!") }
                        .addOnFailureListener { e -> Log.w("FIRESTORE_UPDATE_TICKET", "Error updating ticket", e) }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_UPDATE_TICKET", "Error fetching ticket", e)
            }
    }

}