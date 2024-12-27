package com.example.luckylotto.data.repository.prize_request_repository

import android.util.Log
import com.example.luckylotto.data.model.PrizeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await

class OnlinePrizeRequestRepository {

    private val prizeRequestCollectionName = "prizeRequest"
    companion object {
        val instance: OnlinePrizeRequestRepository by lazy { OnlinePrizeRequestRepository() }
    }

    suspend fun prizeRequest(firebaseDB: FirebaseFirestore, prizeRequest: PrizeRequest): Boolean {
        return try {
            firebaseDB.collection(prizeRequestCollectionName)
                .add(prizeRequest)
                .addOnSuccessListener { documentReference ->
                    Log.d("FIRESTORE_PRIZE_REQUEST", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("FIRESTORE_PRIZE_REQUEST", "Error adding document", e)
                }
                .await()
            true
        } catch (_: Exception) {
            false
        }

    }

}