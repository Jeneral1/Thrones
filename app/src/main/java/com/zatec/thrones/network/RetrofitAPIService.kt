package com.zatec.thrones.network

import com.zatec.thrones.model.House
import com.zatec.thrones.model.GoTCharacter
import com.zatec.thrones.ui.theme.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitAPIService{
    @GET("houses")
    suspend fun getHousesList(@Query("page") page: Int) : List<House>

    @GET
    suspend fun getHouse(@Url url: String) : House

    @GET
    suspend fun getCharacter(@Url url: String) : GoTCharacter

}

object RetrofitClient{
    private fun getClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val apiService: RetrofitAPIService =
        getClient().create(RetrofitAPIService::class.java)
}