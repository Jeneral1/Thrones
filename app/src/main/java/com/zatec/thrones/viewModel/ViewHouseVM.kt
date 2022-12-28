package com.zatec.thrones.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatec.thrones.network.RetrofitClient
import kotlinx.coroutines.launch

/**
 * View model instance of [ViewModel] for ViewHouseScreen. Used to encapsulate the
 * data for the ViewHouseScreen and hold the state of the elements on the screen
 * */
class ViewHouseVM: ViewModel() {

    private var url by mutableStateOf("")
    var name by mutableStateOf("")
    var region by mutableStateOf("")
    var coatOfArms by mutableStateOf("")
    var words by mutableStateOf("")
    var titles by mutableStateOf("")
    var seats by mutableStateOf("")
    var currentLord by mutableStateOf("")
    var heir by mutableStateOf("")
    var overlord by mutableStateOf("")

    var errorMessage: String by mutableStateOf("")

    /**
     * Launches a coroutineScope with [viewModelScope] to run an Https API call
     * using [RetrofitClient.apiService] to get House and some GoTCharacters.
     * The states of mutable variables are changed after the running in the coroutine scope
     *
     * @param[urL] the Url to the house
     * */
    fun getHouse(urL: String){
            viewModelScope.launch {
                val service = RetrofitClient.apiService
                try {
                    val house = service.getHouse(urL)
                    house.let {
                        if (it.currentLord.isNotEmpty()) {
                            this.launch {
                                val character = service.getCharacter(it.currentLord)
                                currentLord = character.name

                            }
                        }
                        if (it.heir.isNotEmpty()) {
                            this.launch {
                                val character = service.getCharacter(it.heir)
                                heir = character.name

                            }
                        }
                        if (it.overlord.isNotEmpty()) {
                            this.launch {
                                val overlordHouse = service.getHouse(it.overlord)
                                overlord = overlordHouse.name
                            }
                        }
                    }

                    url = house.url
                    name = house.name
                    region = house.region
                    coatOfArms = house.coatOfArms
                    words = house.words
                    titles = house.titles.toString()
                    seats = house.seats.toString()


                }catch (e: Exception){
                    e.localizedMessage?.let { errorMessage = it }
                }
            }
    }
}