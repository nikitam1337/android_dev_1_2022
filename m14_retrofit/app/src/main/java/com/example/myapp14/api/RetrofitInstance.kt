package com.example.myapp14.api

import com.example.myapp14.data.PersonsList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me"

object RetrofitInstance{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchPersonApi: SearchPersonApi = retrofit.create(SearchPersonApi::class.java)

}

interface SearchPersonApi {
    @GET("/api")
    suspend fun getPersonsInfo(): Response<PersonsList>
}