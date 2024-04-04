package com.example.recyclerview.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MarsPhotoListViewModelFactory @Inject constructor(private val marsPhotoListViewModel: MarsPhotoListViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarsPhotoListViewModel::class.java)) {
            return marsPhotoListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
