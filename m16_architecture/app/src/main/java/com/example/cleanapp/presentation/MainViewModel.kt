package com.example.cleanapp.presentation

import androidx.lifecycle.ViewModel
import com.example.cleanapp.data.UsefulActivityDto
import com.example.cleanapp.domain.GetUsefulActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {

    private val defaultActivity = UsefulActivityDto(
        "default",
        0,
        "default"
    )
    private val _activityFlow = MutableStateFlow(defaultActivity)
    val activityFlow = _activityFlow.asStateFlow()

    suspend fun reloadUsefulActivity() {
        _activityFlow.value = getUsefulActivityUseCase.execute()
    }
}