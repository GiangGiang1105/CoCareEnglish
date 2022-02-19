package com.example.cocarelish.data.essay.repository

import android.util.Log
import com.example.cocarelish.base.BaseResponse
import com.example.cocarelish.data.essay.remote.api.EssayApi
import com.example.cocarelish.data.essay.remote.dto.SaveEssay
import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.*
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EssayRepositoryImpl @Inject constructor(private val essayApi: EssayApi) : EssayRepository() {

    override fun getAllLevels(): Flow<Resource<LevelEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllLevels().data }
            Log.d("TAG", "getAllLevels: $response")
            emit(response)
        }
    }

    override fun getAllTypes(): Flow<Resource<TypeEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllTypes().data }
            Log.d("TAG", "getAllTypes: $response")
            emit(response)
        }
    }

    override fun getAllTopicsById(topic_id: Int): Flow<Resource<TopicEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllTopicsById(topic_id).data }
            Log.d("TAG", "getAllTopicsById: $response")
            emit(response)
        }
    }

    override fun getAllTestById(test_id: Int): Flow<Resource<TestEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getDetailTest(test_id).data }
            Log.d("TAG", "getAllTestById: $response")
            emit(response)
        }
    }

    override fun getAllEssayOfUser(user_id: String): Flow<Resource<EssayOfUserEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllEssayOfUser(user_id).data }
            Log.d("TAG", "getAllEssayOfUser: $response")
            emit(response)
        }
    }

    override fun getAllTestByTopic(id: Int): Flow<Resource<TestByTopicEntity>> {
        return flow {
            val response = safeApiCall { essayApi.getAllTestByTopic(id).data }
            Log.d("TAG", " getAllTestByTopic: $response")
            emit(response)
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