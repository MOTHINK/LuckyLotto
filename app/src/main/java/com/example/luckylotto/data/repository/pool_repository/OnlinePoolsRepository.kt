package com.example.luckylotto.data.repository.pool_repository

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OnlinePoolsRepository() {
    private val poolCollectionName = "pools"

    companion object {
        val instance: OnlinePoolsRepository by lazy { OnlinePoolsRepository() }
    }

    suspend fun insertPool(firebaseDB: FirebaseFirestore, pool: Pool, poolFirebaseDocumentReferenceId: (String) -> Unit): Boolean {
        return try {
            firebaseDB.collection(poolCollectionName).add(pool)
                .addOnSuccessListener { documentReference ->
                    poolFirebaseDocumentReferenceId(documentReference.id)
                    Log.d("FIRESTORE_INSERT_POOL", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e -> Log.w("FIRESTORE_INSERT_POOL", "Error adding document", e) }
                .await()
            true
        } catch (_: Exception) {
            false
        }
    }

    fun getAllPoolsStream(firebaseDB: FirebaseFirestore, currentTime: Long, onSuccess: (List<Pool>) -> Unit) {
        firebaseDB.collection(poolCollectionName).whereGreaterThan("closeTime", currentTime)
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

    fun deletePool(firebaseDB: FirebaseFirestore, pool: Pool): Boolean {
        return try {
            true
        } catch (_: Exception) {
            false
        }
    }

    fun deletePoolById(firebaseDB: FirebaseFirestore, id: String): Boolean {
        return try {
            true
        } catch (_: Exception) {
            false
        }
    }

    fun incrementBoughtTickets(firebaseDB: FirebaseFirestore, pool: Pool): Boolean {
        return try {
            true
        } catch (_: Exception) {
            false
        }
    }

}