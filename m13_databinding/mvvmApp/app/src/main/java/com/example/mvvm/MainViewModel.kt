package com.example.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {
    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _searchResult = MutableLiveData<String>()
    val searchResult: LiveData<String> = _searchResult

    val searchText = MutableStateFlow<String>("")

    private var searchJob: Job? = null

    private var isSearchInProgress = false
        private set

    init {
        searchText.debounce(300).onEach { query ->
            if (query.length >= 3) {
                onButtonSearchClick(query)
            }
        }.launchIn(viewModelScope)
    }

    fun onButtonSearchClick(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            isSearchInProgress = true
            _isSearching.value = true

            delay(5000)

            isSearchInProgress = false
            _isSearching.value = false
            _searchResult.value = "По запросу $query ничего не найдено"
        }
    }
}