package com.zatec.thrones.network

import com.zatec.thrones.model.House
import com.zatec.thrones.model.GoTCharacter
import com.zatec.thrones.ui.theme.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitAPIEndpoint{
    @GET("houses")
    suspend fun getHousesList(@Query("page") page: Int) : List<House>

    @GET
    suspend fun getHouse(@Url url: String) : House

    @GET
    suspend fun getCharacter(@Url url: String) : GoTCharacter

}

object RetrofitClient{
    fun getClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val apiService: RetrofitAPIEndpoint =
        getClient().create(RetrofitAPIEndpoint::class.java)
}