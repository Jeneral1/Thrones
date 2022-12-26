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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zatec.thrones.screens.GetHousesScreen
import com.zatec.thrones.screens.ViewHouseScreen
import com.zatec.thrones.ui.theme.ThronesTheme
import com.zatec.thrones.viewModel.HousesViewModel
import com.zatec.thrones.viewModel.ViewHouseVM

class MainActivity : ComponentActivity() {
    private val viewHouseVM: ViewHouseVM by viewModels()
    private val housesViewModel: HousesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThronesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyGotApp(viewHouseVM = viewHouseVM, housesViewModel = housesViewModel)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGotApp(modifier: Modifier = Modifier, viewHouseVM: ViewHouseVM, housesViewModel: HousesViewModel) {
    val navController = rememberNavController()

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
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            GoTNavHost(navHostController = navController, viewHouseVM, housesViewModel)
        }
    }
}

@Composable
fun  GoTNavHost(navHostController: NavHostController, viewHouseVM: ViewHouseVM, housesViewModel: HousesViewModel){
    NavHost(
        navController = navHostController,
        startDestination = GoTScreen.GetHouses.name
    ){
        composable(GoTScreen.GetHouses.name){
            GetHousesScreen(
                housesViewModel,
                onItemClick = {houseId ->
                    navHostController.navigate(
                        "${GoTScreen.ViewHouse.name}/$houseId"
                    )
                },
            )
        }
        composable(
            "${GoTScreen.ViewHouse.name}/{houseId}",
            arguments = listOf(
                navArgument("houseId"){type = NavType.StringType}
            )
        ){
            val houseId = it.arguments?.getString("houseId")
            if (houseId != null) {
                ViewHouseScreen(viewHouseVM,houseId = houseId)
            }
        }
    }
}

