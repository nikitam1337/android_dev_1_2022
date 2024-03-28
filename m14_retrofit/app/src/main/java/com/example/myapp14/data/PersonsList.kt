package com.example.myapp14.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonsList(
    @Json(name = "results") val results: List<Result>
)