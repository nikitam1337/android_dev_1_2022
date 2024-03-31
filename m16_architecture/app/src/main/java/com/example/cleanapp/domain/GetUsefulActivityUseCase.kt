package com.example.cleanapp.domain

import com.example.cleanapp.data.UsefulActivitiesRepository
import com.example.cleanapp.data.UsefulActivityDto
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val usefulActivitiesRepository: UsefulActivitiesRepository
) {
    suspend fun execute(): UsefulActivityDto {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}