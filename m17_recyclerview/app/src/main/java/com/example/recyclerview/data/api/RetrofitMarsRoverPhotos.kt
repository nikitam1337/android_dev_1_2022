package com.example.recyclerview.data.api

import com.example.recyclerview.data.MarsPhotosList
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"
private const val API_KEY =  "SrN14pxnCNWc9fLAlFzcl3l42CjQn6bM0IpoqPD8"

object RetrofitMarsRoverPhotos{
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchMarsPhotosAPI: SearchMarsPhotosAPI = retrofit.create(SearchMarsPhotosAPI::class.java)

}

interface SearchMarsPhotosAPI {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos?earth_date=")
    suspend fun getMarsPhotosList(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): MarsPhotosList
}