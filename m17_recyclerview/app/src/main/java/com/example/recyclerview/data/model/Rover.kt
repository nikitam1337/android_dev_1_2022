package com.example.recyclerview.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rover(
    val name : String
) :Parcelable
