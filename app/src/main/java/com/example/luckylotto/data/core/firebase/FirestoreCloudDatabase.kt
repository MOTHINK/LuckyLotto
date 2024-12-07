package com.example.luckylotto.data.core.firebase

import android.util.Log
import com.example.luckylotto.data.model.Pool
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach

class FirestoreCloudDatabase() {


    companion object {

        private val TAG = "XXX"

        fun createPool(db: FirebaseFirestore,pool: Pool) {
            db.collection("pools")
                .add(pool)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

        fun getAllPools(db: FirebaseFirestore): Flow<List<Pool>> {
            var x: Flow<List<Pool>> = emptyFlow()
//                db.collection("pools")
//                .get()
//                .addOnSuccessListener { result ->
//                    x = flow { result.toObjects(Pool::class.java) }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
            return x
        }

    }

}