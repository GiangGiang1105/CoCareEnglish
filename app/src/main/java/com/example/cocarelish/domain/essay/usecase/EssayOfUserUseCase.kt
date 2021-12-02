package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.EssayOfUserEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EssayOfUserUseCase @Inject constructor(private val essayRepository: EssayRepository) {

    fun execute(essay_id: Int): Flow<Resource<EssayOfUserEntity>> {
        return essayRepository.getAllEssayOfUser(essay_id)
    }
}