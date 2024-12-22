package com.example.luckylotto.data.repository.prize_request_repository

import android.util.Log
import com.example.luckylotto.data.model.PrizeRequest
import com.google.firebase.firestore.FirebaseFirestore

class OnlinePrizeRequestRepository {

    private val prizeRequestCollectionName = "prizeRequest"
    companion object {
        val instance: OnlinePrizeRequestRepository by lazy { OnlinePrizeRequestRepository() }
    }

    fun prizeRequest(firebaseDB: FirebaseFirestore, prizeRequest: PrizeRequest) {
        firebaseDB.collection(prizeRequestCollectionName)
            .add(prizeRequest)
            .addOnSuccessListener { documentReference ->
                Log.d("FIRESTORE_PRIZE_REQUEST", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FIRESTORE_PRIZE_REQUEST", "Error adding document", e)
            }
    }

}