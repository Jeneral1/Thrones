package com.zatec.thrones

enum class GoTScreen {
    GetHouses,
    ViewHouse;

    companion object{
        fun fromRoute(route: String?): GoTScreen =
            when (route?.substringBefore("/")){
                ViewHouse.name -> ViewHouse
                GetHouses.name -> GetHouses
                null -> GetHouses
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}