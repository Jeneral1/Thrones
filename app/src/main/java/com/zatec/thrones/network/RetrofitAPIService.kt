package com.zatec.thrones.network

import com.zatec.thrones.model.House
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://www.anapioficeandfire.com/api/"

interface RetrofitAPIService{
    @GET("houses")
    suspend fun getHousesList(@Query("page") page: Int) : List<House>

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