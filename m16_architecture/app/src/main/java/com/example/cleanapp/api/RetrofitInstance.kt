package com.example.cleanapp.api

import com.example.cleanapp.data.UsefulActivityDto
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.boredapi.com"

object RetrofitInstance{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchPersonApi: SearchUsefulActivityAPI = retrofit.create(SearchUsefulActivityAPI::class.java)

}

interface SearchUsefulActivityAPI {
    @GET("/api/activity")
    suspend fun getUsefulActivity(): UsefulActivityDto
}