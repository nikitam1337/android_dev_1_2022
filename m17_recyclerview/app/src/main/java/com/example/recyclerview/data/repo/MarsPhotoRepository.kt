package com.example.recyclerview.data.repo

import com.example.recyclerview.data.MarsPhotosList
import com.example.recyclerview.data.api.RetrofitMarsRoverPhotos
import javax.inject.Inject


class MarsPhotoRepository @Inject constructor() {

    /**
     * @param date дата снимка
     * @return MarsPhotosList возвращает Лист фото
     */
    suspend fun loadMarsPhoto(date: String): MarsPhotosList {
        return RetrofitMarsRoverPhotos.searchMarsPhotosAPI.getMarsPhotosList(date)
    }
}