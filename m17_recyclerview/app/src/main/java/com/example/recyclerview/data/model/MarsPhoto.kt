package com.example.recyclerview.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsPhoto(
    @SerializedName("img_src") val imgSrc: String,
    @SerializedName("earth_date") val earthDate: String,
    val sol: Int,
    val camera: Camera,
    val rover: Rover
) : Parcelable