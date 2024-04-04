package com.example.recyclerview.data

import android.os.Parcelable
import com.example.recyclerview.data.model.MarsPhoto
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhotosList(
    val photos: List<MarsPhoto>
) : Parcelable
