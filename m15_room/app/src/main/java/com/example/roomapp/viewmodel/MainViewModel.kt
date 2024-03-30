package com.example.roomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.db.Word
import com.example.roomapp.data.db.WordDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {
    private val _state = MutableStateFlow(State.START)
    val state = _state.asStateFlow()

    val getFirst5Words = this.wordDao.getFirst5Words().shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        replay = 1
    )

    fun onAdd(newWord: String) {
        viewModelScope.launch {
            when (wordDao.getSearchWord(newWord)) {
                null -> wordDao.addNewWord(Word(newWord, 1))
                else -> wordDao.update(newWord)
            }
        }
    }

    fun changeState(text: CharSequence?) {
        if (!text.isNullOrEmpty() && text.matches(Regex("""^[А-Яа-яA-Za-z\-]{3,}${'$'}""")))
            _state.value = State.SUCCESS
        else _state.value = State.ERROR
    }

    fun onDelete() {
        viewModelScope.launch {
            wordDao.deleteAll()
        }
    }
}