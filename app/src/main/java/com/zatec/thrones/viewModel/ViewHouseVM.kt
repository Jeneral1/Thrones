package com.zatec.thrones.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zatec.thrones.network.RetrofitClient
import kotlinx.coroutines.launch

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

    fun getHouse(urL: String){
            viewModelScope.launch {
                try {
                    val house = RetrofitClient.apiService.getHouse(urL)
                    house.let {
                        if (it.currentLord.isNotEmpty()) {
                            this.launch {
                                val character = RetrofitClient.apiService.getCharacter(it.currentLord)
                                currentLord = character.name

                            }
                        }
                        if (it.heir.isNotEmpty()) {
                            this.launch {
                                val character = RetrofitClient.apiService.getCharacter(it.heir)
                                heir = character.name

                            }
                        }
                        if (it.overlord.isNotEmpty()) {
                            this.launch {
                                val overlordHouse = RetrofitClient.apiService.getHouse(it.overlord)
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