package com.example.luckylotto.data.repository.pool_repository

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.google.firebase.firestore.FirebaseFirestore

class OnlinePoolsRepository() {
    companion object {
        val instance: OnlinePoolsRepository by lazy { OnlinePoolsRepository() }
    }
    fun getAllPoolsStream(firebaseDB: FirebaseFirestore, currentTime: Long, onSuccess: (List<Pool>) -> Unit) {
        firebaseDB.collection("pools")
            .whereGreaterThan("closeTime", currentTime)
            .get()
            .addOnSuccessListener { result ->
                if(!result.isEmpty) onSuccess(result.toObjects(Pool::class.java))
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents.", exception)
            }
    }

    fun getPool(firebaseDB: FirebaseFirestore, id: Int, onSuccess: () -> Unit) {
        firebaseDB.collection("pools").whereEqualTo("poolId",id).get()
            .addOnSuccessListener { result ->
//                if (!result.isEmpty) flow { result.toObjects(Pool::class.java).asFlow() }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching user by name", e)
            }
    }

    fun insertPool(firebaseDB: FirebaseFirestore, pool: Pool, onSuccess: () -> Unit) {
        firebaseDB.collection("pools")
            .add(pool)
            .addOnSuccessListener { documentReference ->
                Log.d("XXX", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("XXX", "Error adding document", e)
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