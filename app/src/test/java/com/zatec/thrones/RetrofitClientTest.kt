package com.zatec.thrones

import com.zatec.thrones.model.GoTCharacter
import com.zatec.thrones.model.House
import com.zatec.thrones.network.RetrofitAPIEndpoint
import com.zatec.thrones.network.RetrofitClient
import com.zatec.thrones.ui.theme.BASE_URL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class RetrofitClientTest {
    private val remoteApi = RetrofitClient.getClient()
    private val service = RetrofitService(RetrofitClient.getClient())


    @Test
    fun retrofitInstanceTest (){

        assert(remoteApi.baseUrl().toUrl().toString()== BASE_URL)
    }
    @Test
    fun retrofitInstanceTest_getHousesList() = runTest{
        val page = 1

        val housesList = service.getHousesList(page)
        assert(housesList.isNotEmpty())
        assert(housesList.count()==10)
    }

    @Test
    fun retrofitInstanceTest_getHouse() = runTest{
        val url = BASE_URL
        val expectedHouse = House(
            "https://www.anapioficeandfire.com/api/houses/10",
            "House Baelish of Harrenhal",
            "The Riverlands",
            "A field of silver mockingbirds, on a green field(Vert, semé of mockingbirds argent)",
            "",
            arrayOf("Lord Paramount of the Trident","Lord of Harrenhal").toList(),
            arrayOf("Harrenhal").toList(),
            "https://www.anapioficeandfire.com/api/characters/823",
            "",
            "https://www.anapioficeandfire.com/api/houses/16",)

        val house = service.getHouse("$url/houses/10")
        assert(house == expectedHouse)
    }


    @Test
    fun retrofitInstanceTest_getCharacter() = runTest{
        val url = BASE_URL
        val expectedCharacter = GoTCharacter(
            "https://www.anapioficeandfire.com/api/characters/823",
            "Petyr Baelish",
            "Male",
        )

        val gotCharacter = service.getCharacter("$url/characters/823")
        assert(gotCharacter==expectedCharacter)
    }


}

class RetrofitService constructor(private val retrofit: Retrofit): RetrofitAPIEndpoint{
    private val endpoint by lazy { retrofit.create(RetrofitAPIEndpoint::class.java) }

    override suspend fun getHousesList(page: Int): List<House> =
        endpoint.getHousesList(page)

    override suspend fun getHouse(url: String): House =
        endpoint.getHouse(url)

    override suspend fun getCharacter(url: String): GoTCharacter=
        endpoint.getCharacter(url)

}