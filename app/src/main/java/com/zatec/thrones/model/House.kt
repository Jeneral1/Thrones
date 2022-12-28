package com.zatec.thrones.model

import com.squareup.moshi.Json

/**
 * Data class that models a Game of Thrones House
 *
 * @param url Url of the House of type:{@link String}
 * @param name Name of the House of type:{@link String}
 * @param region region of the House of type:{@link String}
 * @param coatOfArms Coat of Arms of the House of type:{@link String}
 * @param words Slogan of the House of type:{@link String}
 * @param titles List of Titles {@link String} which are held by the house
 * @param seats List of Seats {@link String} which are held by the house
 * @param currentLord Url of a [GoTCharacter] rules the house
 * @param heir Url of a [GoTCharacter] who is the heir to the throne of the House
 * @param overlord Url of the [House] which this House answers to
 *
 * */
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

