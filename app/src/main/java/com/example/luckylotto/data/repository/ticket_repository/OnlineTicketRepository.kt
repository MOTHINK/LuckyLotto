package com.example.luckylotto.data.repository.ticket_repository

import android.util.Log
import com.example.luckylotto.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class OnlineTicketRepository {

    private val ticketCollectionName = "tickets"

    companion object {
        val instance: OnlineTicketRepository by lazy { OnlineTicketRepository() }
    }

    suspend fun insertTicket(firebaseDB: FirebaseFirestore, ticket: Ticket): Boolean {
        return try {
            firebaseDB.collection(ticketCollectionName)
                .add(ticket)
                .addOnSuccessListener { documentReference -> Log.d("FIRESTORE_INSERT_TICKET", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e -> Log.w("FIRESTORE_INSERT_TICKET", "Error adding document", e)
                }
                .await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun updateTicketPrizeClaim(firebaseDB: FirebaseFirestore, ticket: Ticket): Boolean {
        var documentId = ""
        firebaseDB.collection(ticketCollectionName).whereEqualTo("ticketId", ticket.ticketId)
            .get()
            .addOnSuccessListener {
                Log.d("FIRESTORE_GET_TICKET", "DocumentSnapshot successfully retrieved!")
                if(!it.isEmpty) {
                    documentId = it.documents[0].id
                }
            }
            .addOnFailureListener {
                    e -> Log.w("FIRESTORE_GET_TICKET", "Error getting ticket", e)
            }.await()

        if(documentId.isNotEmpty()) {
            return try {
                firebaseDB.collection(ticketCollectionName).document(documentId)
                    .update("prizeClaimed", true)
                    .addOnSuccessListener {
                        Log.d("FIRESTORE_UPDATE_TICKET", "DocumentSnapshot successfully updated!")
                    }
                    .addOnFailureListener { e -> Log.w("FIRESTORE_UPDATE_TICKET", "Error updating ticket", e)
                    }.await()
                true
            } catch (_e: Exception) {
                false
            }
        }
        return false
    }

}