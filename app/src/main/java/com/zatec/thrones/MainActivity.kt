package com.zatec.thrones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.zatec.thrones.screens.GetHousesScreen
import com.zatec.thrones.screens.ViewHouseScreen
import com.zatec.thrones.ui.theme.ThronesTheme
import com.zatec.thrones.viewModel.HousesViewModel
import com.zatec.thrones.viewModel.ViewHouseVM

class MainActivity : ComponentActivity() {
    val housesViewModel: ViewHouseVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThronesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyGotApp(viewModel = housesViewModel)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGotApp(modifier: Modifier = Modifier, viewModel: ViewHouseVM) {
    Scaffold(
        topBar = {
            TopAppBar (title = {
                Column {
                    Text(text = "Game of Thrones", style = MaterialTheme.typography.titleLarge, fontFamily = FontFamily.Cursive, fontSize = 30.sp)
                    Text(text = "Houses", style = MaterialTheme.typography.titleMedium)
                }})
        }
    ) {
        Surface(
            modifier = modifier.fillMaxSize().padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            ViewHouseScreen(viewModel, "https://www.anapioficeandfire.com/api/houses/10")
        }
    }
}

