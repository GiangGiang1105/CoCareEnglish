package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.TopicEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopicUseCase  @Inject constructor(private val essayRepository: EssayRepository){

    fun execute(topic_id: Int): Flow<Resource<TopicEntity>> {
        return essayRepository.getAllTopicsById(topic_id)
    }
}