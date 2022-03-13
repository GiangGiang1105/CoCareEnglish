package com.example.cocarelish.data.authentication.repository

import android.util.Log
import com.example.cocarelish.data.authentication.remote.api.AuthApi
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.AuthRepository
import com.example.cocarelish.domain.auth.entity.LoginEntity
import com.example.cocarelish.domain.auth.entity.RegisterEntity
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.const.FireBaseConst.COLLECTION_USER
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val firebaseStore: FirebaseFirestore
) : AuthRepository() {

    companion object {
        const val TAG = "AuthRepository"
    }

    override fun login(loginRequest: LoginRequest): Flow<Resource<LoginEntity>> {
        return flow {
            val response = safeApiCall { authApi.login(loginRequest).data }
            Log.d("TAG", "login: $response")
            emit(response)
        }
    }

    override fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterEntity>> {
        return flow {
            val response = safeApiCall {
                authApi.register(registerRequest).data
            }
            Log.d("TAG", "register: $response")
            emit(response)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserInformation(user_id: String): Flow<Resource<UserInfo?>> {
        return callbackFlow {
            firebaseStore.collection(COLLECTION_USER).document(user_id)
                .get().addOnCompleteListener {
                    it.result.toObject(UserInfo::class.java)?.let { userInfo ->
                        trySend(Resource.Success(userInfo))
                    }
                }
            awaitClose {  }
        }
    }


    override suspend fun setUpUserInformation(userInformation: UserInfo): Boolean {
        var data = false

        withContext(Dispatchers.IO) {
            firebaseStore.collection(COLLECTION_USER).document(userInformation.id)
                .set(userInformation).addOnCompleteListener {
                    data = it.isSuccessful
                }.addOnFailureListener {
                    Log.d(TAG, "setUpUserInformation: ${it.message}")
                }
        }
        return data
    }
}