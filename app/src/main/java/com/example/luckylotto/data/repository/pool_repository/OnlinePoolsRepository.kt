package com.example.luckylotto.data.repository.pool_repository

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OnlinePoolsRepository() {
    private val poolCollectionName = "pools"
    private val ticketCollectionName = "tickets"
    companion object {
        val instance: OnlinePoolsRepository by lazy { OnlinePoolsRepository() }
    }
    fun getAllPoolsStream(firebaseDB: FirebaseFirestore, currentTime: Long, onSuccess: (List<Pool>) -> Unit) {
        firebaseDB.collection(poolCollectionName)
            .whereGreaterThan("closeTime", currentTime)
            .get()
            .addOnSuccessListener { result ->
                if(!result.isEmpty) onSuccess(result.toObjects(Pool::class.java))
            }
            .addOnFailureListener { exception ->
                Log.w("FIRESTORE_GET_ALL_POOLS", "Error getting documents.", exception)
            }
    }

    fun getPool(firebaseDB: FirebaseFirestore, poolId: String, onSuccess: (Pool) -> Unit) {
        firebaseDB.collection(poolCollectionName).whereEqualTo("poolId",poolId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) onSuccess(result.toObjects(Pool::class.java)[0])
            }
            .addOnFailureListener { e ->
                Log.e("FIRESTORE_GET_POOL", "Error fetching user by name", e)
            }
    }

    fun insertPool(firebaseDB: FirebaseFirestore, pool: Pool) {
        firebaseDB.collection(poolCollectionName)
            .add(pool)
            .addOnSuccessListener { documentReference ->
                Log.d("FIRESTORE_INSERT_POOL", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FIRESTORE_INSERT_POOL", "Error adding document", e)
            }
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

    fun deletePool(firebaseDB: FirebaseFirestore, pool: Pool, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    fun deletePoolById(firebaseDB: FirebaseFirestore, id: String, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    fun updatePool(firebaseDB: FirebaseFirestore, pool: Pool, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

}