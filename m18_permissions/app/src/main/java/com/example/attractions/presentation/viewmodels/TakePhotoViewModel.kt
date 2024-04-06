package com.example.attractions.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.data.db.Repository
import com.example.attractions.data.model.Photo
import kotlinx.coroutines.launch
import javax.inject.Inject

class TakePhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getUriLastPhoto(context: Context): String {
        return repository.getUriLastPhoto(context)
    }

    fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            repository.insertData(photo)
        }
    }
}