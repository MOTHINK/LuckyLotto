package com.example.luckylotto.data.repository.pool_repository

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.example.luckylotto.data.model.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OnlinePoolsRepository() {
    private val poolCollectionName = "pools"
    companion object {
        val instance: OnlinePoolsRepository by lazy { OnlinePoolsRepository() }
    }
    fun getAllPoolsStream(firebaseDB: FirebaseFirestore, currentTime: Long, onSuccess: (List<Pool>) -> Unit) {
        firebaseDB.collection(poolCollectionName)
            .whereGreaterThan("closeTime", currentTime)
            .get()
            .addOnSuccessListener { result -> if(!result.isEmpty) onSuccess(result.toObjects(Pool::class.java)) }
            .addOnFailureListener { exception -> Log.w("FIRESTORE_GET_ALL_POOLS", "Error getting documents.", exception) }
    }

    suspend fun getPool(firebaseDB: FirebaseFirestore, poolId: String, onSuccess: (Pool) -> Unit): Boolean {
        return try {
            firebaseDB.collection(poolCollectionName).whereEqualTo("poolId",poolId)
                .get()
                .addOnSuccessListener { result -> if (!result.isEmpty) onSuccess(result.toObjects(Pool::class.java)[0]) }
                .addOnFailureListener { e -> Log.e("FIRESTORE_GET_POOL", "Error fetching pool", e) }
                .await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun insertPool(firebaseDB: FirebaseFirestore, pool: Pool, inserted: (Boolean) -> Unit) {
        firebaseDB.collection(poolCollectionName)
            .add(pool)
            .addOnSuccessListener {
                documentReference -> Log.d("FIRESTORE_INSERT_POOL", "DocumentSnapshot added with ID: ${documentReference.id}")
                inserted(true)
            }
            .addOnFailureListener {
                e -> Log.w("FIRESTORE_INSERT_POOL", "Error adding document", e)
            }
            .await()
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