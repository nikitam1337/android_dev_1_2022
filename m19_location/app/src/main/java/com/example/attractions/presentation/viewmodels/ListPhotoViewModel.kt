package com.example.attractions.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.attractions.data.db.Repository
import javax.inject.Inject

class ListPhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val listPhotos = repository.getAllData()
}
