package com.zatec.thrones.model

import com.squareup.moshi.Json
/**
 * Data class that models a Game of Thrones Character
 *
 * @param url url of the character of type: String
 * @param name name of the character of type: String
 * @param gender gender of the character of type: String
 * */
data class GoTCharacter (
    @field: Json(name = "url")
    val url: String,
    @field: Json(name = "name")
    val name: String,
    @field: Json(name = "gender")
    val gender: String,
)