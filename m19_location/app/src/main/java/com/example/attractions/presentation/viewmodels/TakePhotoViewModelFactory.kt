package com.example.attractions.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class TakePhotoViewModelFactory @Inject constructor(
    private val takePhotoViewModel: TakePhotoViewModel
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TakePhotoViewModel::class.java)) {
            return takePhotoViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}