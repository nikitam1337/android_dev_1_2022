package com.example.attractions.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.data.db.Repository
import com.example.attractions.presentation.StateTakePhotoFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TakePhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow(StateTakePhotoFragment.MAKE_PHOTO)
    val stateFragment = _state.asStateFlow()


    fun insertPhoto(context: Context) {
        viewModelScope.launch {
            repository.insertData(context)
        }
    }


    fun takePicture() {
        viewModelScope.launch {
            delay(1000)
            _state.value = StateTakePhotoFragment.READY_PHOTO
        }
    }

    fun retakePhoto() {
        _state.value = StateTakePhotoFragment.MAKE_PHOTO
    }


}