package com.example.cleanapp.data

import com.example.cleanapp.entity.UsefulActivity
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class UsefulActivityDto @Inject constructor(
    @SerializedName("activity") override val activity: String,
    @SerializedName("participants") override val participants: Int,
    @SerializedName("type") override val type: String
) : UsefulActivity
