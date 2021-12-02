package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.TypeEntity
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeUseCase @Inject constructor(private val essayRepository: EssayRepository){

    fun execute(): Flow<Resource<TypeEntity>> {
        return essayRepository.getAllTypes()
    }
}
