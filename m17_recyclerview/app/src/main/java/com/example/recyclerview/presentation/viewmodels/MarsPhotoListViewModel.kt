package com.example.recyclerview.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.data.MarsPhotosList
import com.example.recyclerview.data.repo.MarsPhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarsPhotoListViewModel @Inject constructor(private val repository: MarsPhotoRepository): ViewModel() {

    private val _listPhotoMars = MutableStateFlow(MarsPhotosList(listOf()))
    val listPhotoMars = _listPhotoMars.asStateFlow()

    private val _datePhotos = MutableStateFlow(PHOTO_DATE)
    private val datePhotos = _datePhotos.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _listPhotoMars.value = repository.loadMarsPhoto(datePhotos.value)
        }
    }

    companion object {
        private const val PHOTO_DATE = "2024-01-20"
    }
}