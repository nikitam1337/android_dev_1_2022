package com.example.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _searchResult = MutableLiveData<String>()
    val searchResult: LiveData<String> = _searchResult

    var isSearchInProgress = false
        private set

    fun onButtonSearchClick(query: String) {
        isSearchInProgress = true
        _isSearching.value = true

        viewModelScope.launch {
            delay(5000)
            isSearchInProgress = false
            _isSearching.value = false
            _searchResult.value = "По запросу $query ничего не найдено"
        }
    }

}