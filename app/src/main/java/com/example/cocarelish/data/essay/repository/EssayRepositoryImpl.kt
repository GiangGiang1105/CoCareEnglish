package com.example.cocarelish.data.essay.repository

import android.util.Log
import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.api.EssayApi
import com.example.cocarelish.data.essay.remote.dto.Level
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.essay.remote.dto.Topic
import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.DeadlineEntity
import com.example.cocarelish.domain.essay.entity.EssayOfUserEntity
import com.example.cocarelish.domain.essay.entity.TopicEntity
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.const.FireBaseConst.COLLECTION_LEVEL
import com.example.cocarelish.utils.const.FireBaseConst.COLLECTION_QUESTION
import com.example.cocarelish.utils.const.FireBaseConst.COLLECTION_TYPE
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EssayRepositoryImpl @Inject constructor(
    private val essayApi: EssayApi,
    private val firebaseStore: FirebaseFirestore
) : EssayRepository() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllLevels(): Flow<Resource<List<Level>>> {
        return callbackFlow {
            val listLevel = mutableListOf<Level>()
            firebaseStore.collection(COLLECTION_LEVEL).get().addOnSuccessListener {
                for (document in it.documents) {
                    document.toObject(Level::class.java)?.let { it1 -> listLevel.add(it1) }
                }
                Log.d(TAG, "getAllLevels: $listLevel")
                trySend(Resource.Success(listLevel.toList()))
            }
            awaitClose { }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllTopics(): Flow<Resource<List<Topic>>> {
        return callbackFlow {
            val listType = mutableListOf<Topic>()
            firebaseStore.collection(COLLECTION_TYPE).get().addOnSuccessListener {
                for (document in it.documents) {
                    document.toObject(Topic::class.java)?.let { it1 ->
                        listType.add(it1)
                    }
                }
                Log.d(TAG, "getAllTopic: $listType")
                trySend(Resource.Success(listType.toList()))
            }
            awaitClose { }
        }
    }

    override fun getAllTopicsById(topic_id: Int): Flow<Resource<TopicEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllTopicsById(topic_id).data }
            Log.d("TAG", "getAllTopicsById: $response")
            emit(response)
        }

    }

    override fun getTestById(test_id: Int): Flow<Resource<Test>> {
        return callbackFlow {
            firebaseStore.collection(COLLECTION_QUESTION).document(test_id.toString()).get()
                .addOnCompleteListener {
                    it.result.toObject(Test::class.java)?.let { test ->
                        Log.d(TAG, "getTestById: $test")
                        trySend(Resource.Success(test))
                    }
                }
            awaitClose { }
        }
    }

    override fun getAllEssayOfUser(user_id: String): Flow<Resource<EssayOfUserEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllEssayOfUser(user_id).data }
            Log.d("TAG", "getAllEssayOfUser: $response")
            emit(response)
        }
    }

    override fun getAllQuestionByTopicId(id: Int): Flow<Resource<List<Test>>> {
        return callbackFlow {
            val listTest = mutableListOf<Test>()
            firebaseStore.collection(COLLECTION_QUESTION).whereEqualTo("topic_id", id).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        document.toObject(Test::class.java)?.let { it1 ->
                            listTest.add(it1)
                        }
                    }
                    Log.d(TAG, "getAllTopic: $listTest")
                    trySend(Resource.Success(listTest.toList()))
                }
            awaitClose { }
        }
    }

    override fun getAllDeadline(): Flow<Resource<DeadlineEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllDeadline().data }
            Log.d("TAG", "getAllDeadline: $response")
            emit(response)
        }
    }

    override fun userSaveWrittenEssay(saveEssay: SaveEssay): Flow<Resource<BaseResponse<String>>> {
        return flow {
            val response = safeApiCall { essayApi.userSaveWrittenEssay(saveEssay) }
            Log.d("TAG", "userSaveWrittenEssay:$response")
            emit(response)
        }
    }
}