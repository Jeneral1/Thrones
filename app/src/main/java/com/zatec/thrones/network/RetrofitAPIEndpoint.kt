package com.zatec.thrones.network

import com.zatec.thrones.model.House
import com.zatec.thrones.model.GoTCharacter
import com.zatec.thrones.ui.theme.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Network API endpoints interface
 * */
interface RetrofitAPIEndpoint{
    /**
     * Https get request for a list of houses.
     *
     * @param[page] an annotated query of type: Int
     * @return List of [House]s
     *
     * */
    @GET("houses")
    suspend fun getHousesList(@Query("page") page: Int) : List<House>

    /**
     * Https get request to get a houses
     *
     * @param[url] annotated as Url and type: String. This Url will override the BASE_URL
     * @return a [House] object
     *
     * */
    @GET
    suspend fun getHouse(@Url url: String) : House

    /**
     * Https get request to get a character
     *
     * @param[url] annotated as Url and type: String. This Url will override the BASE_URL
     * @return a [GoTCharacter] object
     *
     * */
    @GET
    suspend fun getCharacter(@Url url: String) : GoTCharacter

}

/**
 * Public object which can be accessed from anywhere within this project
 * */
object RetrofitClient{
    /**
     * A retrofit client function
     *
     * @return instance of the [Retrofit] class which creates an instance using Builder pattern
     * */
    fun getClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /**
     * In implementation of the Https network API endpoints {@link RetrofitAPIEndpoint [RetrofitAPIEndpoint]}
     * */
    val apiService: RetrofitAPIEndpoint =
        getClient().create(RetrofitAPIEndpoint::class.java)
}