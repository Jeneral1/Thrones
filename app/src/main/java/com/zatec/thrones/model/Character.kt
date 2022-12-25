package com.zatec.thrones.model

import com.squareup.moshi.Json

data class Character (
    @field: Json(name = "url")
    val url: String,
    @field: Json(name = "name")
    val name: String,
    @field: Json(name = "gender")
    val gender: String,
)