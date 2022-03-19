package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.data.essay.remote.dto.Level
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.data.essay.remote.dto.Topic
import com.example.cocarelish.data.essay.remote.dto.Type
import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.EssayOfUserEntity
import com.example.cocarelish.domain.essay.entity.TopicEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EssayOfSystemUseCase @Inject constructor(private val essayRepository: EssayRepository) {

    fun getAllEssayOfUser(user_id: String): Flow<Resource<EssayOfUserEntity>> {
        return essayRepository.getAllEssayOfUser(user_id)
    }

    fun getAllLevel(): Flow<Resource<List<Level>>> {
        return essayRepository.getAllLevels()
    }

    fun getAllType(): Flow<Resource<List<Topic>>> {
        return essayRepository.getAllTopics()
    }


    fun getAllQuestionByTopicID(topic_id: Int): Flow<Resource<List<Test>>> {
        return essayRepository.getAllQuestionByTopicId(topic_id)
    }

    fun getEssayByID(test_id: Int): Flow<Test> {
        return essayRepository.getTestById(test_id)
    }

    fun editFavouriteEssay(essay: Test): Flow<Boolean> {
        return essayRepository.editFavouriteEssay(essay)
    }
}