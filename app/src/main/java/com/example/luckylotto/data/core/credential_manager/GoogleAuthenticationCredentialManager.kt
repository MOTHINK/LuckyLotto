package com.example.luckylotto.data.core.credential_manager

import android.content.ContentValues.TAG
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.NoCredentialException
import com.example.luckylotto.R
import com.example.luckylotto.data.core.firebase.FirebaseAuthentication
import com.example.luckylotto.utils.generateNonce
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthResult
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

//    fun startGoogleAuthenticationFlow(coroutineScope: CoroutineScope, context: Context, setFilterByAuthorizedAccounts: Boolean, navigatingTo: () -> Unit, mainViewModel: MainViewModel) {
//        coroutineScope.launch {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                try {
////                    getGoogleCredentials(context,setFilterByAuthorizedAccounts,navigatingTo,mainViewModel)
//                    getGoogleCredentials(context,setFilterByAuthorizedAccounts,navigatingTo,mainViewModel)
//                } catch (e: GetCredentialException) {
//                    handleFailure(e)
//                } catch (e: NoCredentialException) {
//                    try {
////                        getGoogleSignInCredentials(context,navigatingTo,mainViewModel)
//                        getGoogleSignInCredentials(context,navigatingTo,mainViewModel)
//                    } catch (e: GetCredentialException) {
//                        handleFailure(e)
//                    } catch (e: NoCredentialException) {
//                        handleFailure(e)
//                    }
//                    handleFailure(e)
//                }
//            }
//        }
//    }

    fun startGoogleAuthenticationFlow(coroutineScope: CoroutineScope, context: Context, setFilterByAuthorizedAccounts: Boolean, successAuthenticating: (Task<AuthResult>) -> Unit) {
        coroutineScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    getGoogleCredentials(context,setFilterByAuthorizedAccounts) { successAuthenticating(it) }
                } catch (e: GetCredentialException) {
                    handleFailure(e)
                } catch (e: NoCredentialException) {
                    try {
                        getGoogleSignInCredentials(context) { successAuthenticating(it) }
                    } catch (e: GetCredentialException) {
                        handleFailure(e)
                    } catch (e: NoCredentialException) {
                        handleFailure(e)
                    }
                    handleFailure(e)
                }
            }
        }
    }

//    private suspend fun getGoogleCredentials(context: Context, setFilterByAuthorizedAccounts: Boolean, navigatingTo: () -> Unit, mainViewModel: MainViewModel) {
//        val result = CredentialManager.create(context).getCredential(
//            request = getGoogleCredentialRequest(googleAccessRequest(context,setFilterByAuthorizedAccounts)),
//            context = context,
//        )
////        handleSignIn(result, navigatingTo, mainViewModel)
//        handleSignIn(result) {
//            mainViewModel.setFirebaseUser(it.result.user)
//            mainViewModel.getAllRequiredData()
//            mainViewModel.createWallet(Wallet(it.result.user!!.uid))
//            navigatingTo()
//        }
//    }
//
//    private suspend fun getGoogleSignInCredentials(context: Context, navigatingTo: () -> Unit, mainViewModel: MainViewModel) {
//        val result = CredentialManager.create(context).getCredential(
//            request = getGoogleSignInCredentialRequest(googleSignInAccessRequest(context)),
//            context = context,
//        )
////        handleSignIn(result, navigatingTo,mainViewModel)
//        handleSignIn(result) {
//            mainViewModel.setFirebaseUser(it.result.user)
//            mainViewModel.getAllRequiredData()
//            mainViewModel.createWallet(Wallet(it.result.user!!.uid))
//            navigatingTo()
//        }
//    }

    private suspend fun getGoogleCredentials(context: Context, setFilterByAuthorizedAccounts: Boolean, successAuthenticating: (Task<AuthResult>) -> Unit) {
        val result = CredentialManager.create(context).getCredential(request = getGoogleCredentialRequest(googleAccessRequest(context,setFilterByAuthorizedAccounts)), context = context)
        handleSignIn(result) { successAuthenticating(it) }
    }

    private suspend fun getGoogleSignInCredentials(context: Context, successAuthenticating: (Task<AuthResult>) -> Unit) {
        val result = CredentialManager.create(context).getCredential(request = getGoogleSignInCredentialRequest(googleSignInAccessRequest(context)), context = context)
        handleSignIn(result) { successAuthenticating(it) }
    }

    private fun handleFailure(e: Exception) {
        Log.d(e.localizedMessage,e.toString())
    }


//    private fun handleSignIn(result: GetCredentialResponse, navigatingTo: () -> Unit, mainViewModel: MainViewModel) {
//        when (val credential = result.credential) {
//            is PublicKeyCredential -> {
//                credential.authenticationResponseJson
//            }
//
//            is PasswordCredential -> {
//                val username = credential.id
//                val password = credential.password
//            }
//
//            is CustomCredential -> {
//                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    try {
//                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                        FirebaseAuthentication.instance.signInFirebaseAuthentication(googleIdTokenCredential.idToken) {
//                            mainViewModel.setFirebaseUser(it.result.user)
//                            mainViewModel.getAllRequiredData()
//                            mainViewModel.createWallet(Wallet(it.result.user!!.uid))
//                            navigatingTo()
//                        }
//                    } catch (e: GoogleIdTokenParsingException) {
//                        Log.e(TAG, "Received an invalid google id token response", e)
//                    }
//                } else {
//                    Log.e(TAG, "Unexpected type of credential")
//                }
//            }
//
//            else -> {
//                Log.e(TAG, "Unexpected type of credential")
//            }
//        }
//    }

    private fun handleSignIn(result: GetCredentialResponse, successAuthenticating: (Task<AuthResult>) -> Unit) {
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
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        FirebaseAuthentication.instance.signInFirebaseAuthentication(googleIdTokenCredential.idToken) { successAuthenticating(it) }
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