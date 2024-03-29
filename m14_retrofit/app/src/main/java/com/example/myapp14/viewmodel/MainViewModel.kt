package com.example.myapp14.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp14.data.PersonsList
import com.example.myapp14.repo.PersonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = PersonRepository()

    // Состояние загрузки данных
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    // Данные пользователя
    private val _user = MutableStateFlow<PersonsList?>(null)
    val user = _user.asStateFlow()

    private val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    init {
        // Загрузка данных при инициализации ViewModel
        getUser()
    }

    fun getUser() {
        _state.value = State.Loading
        viewModelScope.launch {
            val response = repository.loadUser()
            if (response.isSuccessful) {
                _user.value = response.body()
                _state.value = State.Success
            } else {
                _state.value = State.Error("Ошибка загрузки данных")
            }
        }
    }
}