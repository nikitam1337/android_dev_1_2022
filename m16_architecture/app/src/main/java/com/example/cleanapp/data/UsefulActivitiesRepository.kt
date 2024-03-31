package com.example.cleanapp.data

import com.example.cleanapp.api.RetrofitInstance
import kotlinx.coroutines.delay
import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor(){
    suspend fun getUsefulActivity(): UsefulActivityDto {
        delay(1000)
        return RetrofitInstance.searchPersonApi.getUsefulActivity()
    }
}