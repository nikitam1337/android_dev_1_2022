package com.example.hw11

import android.content.Context
import android.content.Context.MODE_PRIVATE


private const val PREFERENCE_NAME = "preference_name"
private const val KEY_STRING_NAME = "text_from_editText"

class Repository(context: Context) {

    private var localVariable: String? = null
    private var prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)

    private fun getDataFromSharedPreference(): String? {
        return prefs.getString(KEY_STRING_NAME, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return localVariable
    }

    fun saveText(text: String) {
        localVariable = text
        prefs.edit().putString(KEY_STRING_NAME, text).apply()
    }

    fun clearText() {
        localVariable = null
        prefs.edit().clear()
    }

    fun getText(): String {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> "no one source doesn't contain string"
        }
    }
}
