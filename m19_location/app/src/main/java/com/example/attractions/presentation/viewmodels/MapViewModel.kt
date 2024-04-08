package com.example.attractions.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MapViewModel @Inject constructor() : ViewModel() {

    private val listPoint = mutableListOf<Point>(
        Point(59.220422, 39.890752),
        Point(59.220496, 39.893150),
        Point(59.219562, 39.892035),
    )

    val listPoints = listOf(listPoint).asFlow()


}