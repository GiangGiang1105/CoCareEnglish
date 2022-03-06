package com.example.cocarelish.domain.essay.usecase

import com.example.cocarelish.domain.essay.EssayRepository
import com.example.cocarelish.domain.essay.entity.LevelList
import com.example.cocarelish.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LevelUseCase @Inject constructor(private val essayRepository: EssayRepository){

//    fun execute(): Flow<Resource<List<LevelList>>> {
//        return essayRepository.getAllLevels()
//    }
}