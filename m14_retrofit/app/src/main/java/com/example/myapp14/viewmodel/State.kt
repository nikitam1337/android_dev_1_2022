package com.example.myapp14.viewmodel

sealed class State {
    data object Loading : State()
    data object Success : State()
    data class Error(val nameError: String = "Ошибка запроса") : State()
}
