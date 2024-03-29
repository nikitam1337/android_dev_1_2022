package com.example.myapp14.repo;

import com.example.myapp14.api.RetrofitInstance
import com.example.myapp14.data.PersonsList
import kotlinx.coroutines.delay
import retrofit2.Response

public class PersonRepository {

    suspend fun loadUser(): Response<PersonsList> {
        delay(3000)
        return retrofit()
    }

    private suspend fun retrofit(): Response<PersonsList> {
        return RetrofitInstance.searchPersonApi.getPersonsInfo()
    }
}