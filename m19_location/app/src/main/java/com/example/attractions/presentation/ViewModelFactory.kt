package com.example.attractions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attractions.presentation.viewmodels.ListPhotoViewModel
import com.example.attractions.presentation.viewmodels.MapViewModel
import com.example.attractions.presentation.viewmodels.TakePhotoViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val takePhotoViewModel: TakePhotoViewModel,
    private val mapViewModel: MapViewModel,
    private val listPhotoViewModel: ListPhotoViewModel,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TakePhotoViewModel::class.java)) {
            return takePhotoViewModel as T
        }
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return mapViewModel as T
        }
        if (modelClass.isAssignableFrom(ListPhotoViewModel::class.java)) {
            return listPhotoViewModel as T
        }
//        if (modelClass.isAssignableFrom(ItemDonePhotoViewModel::class.java)) {
//            return itemDonePhotoViewModel as T
//        }
        throw IllegalArgumentException("Unknown class name")
    }
}