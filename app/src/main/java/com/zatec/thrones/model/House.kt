package com.zatec.thrones.model

import com.squareup.moshi.Json

data class House(
    @field: Json(name = "url")
    val url: String,
    @field: Json(name = "name")
    val name: String,
    @field: Json(name = "region")
    val region: String,
    @field: Json(name = "coatOfArms")
    val coatOfArms: String,
    @field: Json(name = "words")
    val words: String,
    @field: Json(name = "titles")
    val titles: List<String>,
    @field: Json(name = "seats")
    val seats: List<String>,
    @field: Json(name = "currentLord")
    val currentLord: String,
    @field: Json(name = "heir")
    val heir: String,
    @field: Json(name = "overlord")
    val overlord: String,
)

