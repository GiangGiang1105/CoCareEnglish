package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.TestByTopicEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestByTopicUseCase @Inject constructor(private val essayRepository: EssayRepository){

    fun execute(id: Int): Flow<Resource<List<Test>>> {
        return essayRepository.getAllQuestionByTopicId(id)
    }
}