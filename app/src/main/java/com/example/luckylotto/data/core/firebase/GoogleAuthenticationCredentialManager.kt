package com.example.luckylotto.data.core.firebase

import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.GetCredentialException
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.NoCredentialException
import com.example.luckylotto.R
import com.example.luckylotto.ui.util.generateNonce
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GoogleAuthenticationCredentialManager private constructor() {
    val defaultSetFilterByAuthorizedAccounts: Boolean = true
    companion object {
        val instance: GoogleAuthenticationCredentialManager by lazy { GoogleAuthenticationCredentialManager() }
    }

    private fun googleAccessRequest(context: Context, setFilterByAuthorizedAccounts: Boolean): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(setFilterByAuthorizedAccounts)
            .setServerClientId(context.getString(R.string.oauth_id_client))
            .setAutoSelectEnabled(true)
            .setNonce(generateNonce())
        .build()
    }

    private fun googleSignInAccessRequest(context: Context): GetSignInWithGoogleOption {
        return GetSignInWithGoogleOption.Builder(context.getString(R.string.oauth_id_client))
            .setNonce(generateNonce())
            .build()
    }

    private fun getGoogleCredentialRequest(googleIdOption: GetGoogleIdOption): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun getGoogleSignInCredentialRequest(getSignInWithGoogleOption: GetSignInWithGoogleOption): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(getSignInWithGoogleOption)
            .build()
    }

    fun startGoogleAuthenticationFlow(coroutineScope: CoroutineScope, context: Context, setFilterByAuthorizedAccounts: Boolean) {
        coroutineScope.launch {
            try {
                getGoogleCredentials(context,setFilterByAuthorizedAccounts)
            } catch (e: GetCredentialException) {
                handleFailure(e)
            } catch (e: NoCredentialException) {
                try {
                    getGoogleSignInCredentials(context)
                } catch (e: GetCredentialException) {
                    handleFailure(e)
                } catch (e: NoCredentialException) {
                    handleFailure(e)
                }
                handleFailure(e)
            }
        }
    }

    private suspend fun getGoogleCredentials(context: Context, setFilterByAuthorizedAccounts: Boolean) {
        Log.d("1two3four","Holaa $setFilterByAuthorizedAccounts")
        val result = CredentialManager.create(context).getCredential(
            request = getGoogleCredentialRequest(googleAccessRequest(context,setFilterByAuthorizedAccounts)),
            context = context,
        )
        Log.d("1two3four",result.toString())
        handleSignIn(result)
    }

    private suspend fun getGoogleSignInCredentials(context: Context) {
        Log.d("1two3four","Holaa")
        val result = CredentialManager.create(context).getCredential(
            request = getGoogleSignInCredentialRequest(googleSignInAccessRequest(context)),
            context = context,
        )
        Log.d("1two3four",result.toString())
        handleSignIn(result)
    }

    private fun handleFailure(e: Exception) {
        Log.d(e.localizedMessage,e.toString())
    }


    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is PublicKeyCredential -> {
                credential.authenticationResponseJson
            }

            is PasswordCredential -> {
                val username = credential.id
                val password = credential.password
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        Log.d("1two3four","${googleIdTokenCredential.id} ${googleIdTokenCredential.idToken} ${googleIdTokenCredential.displayName}")
                        FirebaseAuthentication.instance.signInFirebaseAuthentication(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

}