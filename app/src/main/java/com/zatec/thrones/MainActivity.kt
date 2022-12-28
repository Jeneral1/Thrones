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
/**
 * Application starts from [MainActivity] which inherits [ComponentActivity]
 *
 * objects of two @see[viewModels] which are view models for the two pages of the application
 *
 * [onCreate] function marks the begin of the activity. this function is override when the
 * application starts with MainActivity
 * */
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


/**
 * MyGotApp is a composable function that sets the scaffold which contain the App Bar and allows for setting the background
 * by calling [Surface]. The app implements the same Scaffold for all the other screens
 *
 * @param[modifier] That declares the behaviour of the composable children of this class
 * @param[viewHouseVM] An instance of the [ViewHouseVM] which is used to encapsulate the data for the [ViewHouseScreen].
 * It is used to declare the behaviour of the children composable of this class
 * @param[housesViewModel] An instance of the [HousesViewModel]which is used to encapsulate the data for the
 * [GetHousesScreen]. it is used to declare the behaviour of the children composable of this class
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGotApp(modifier: Modifier = Modifier, viewHouseVM: ViewHouseVM, housesViewModel: HousesViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar (title = {
                Column {
                    Text(
                        text = "Game of Thrones",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp)
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


/**
 * GoTNavHost is a composable function that is used to handel the navigation in this application. It calls
 * NavHost function which provides in place in the Compose hierarchy for self contained
 * navigation to occur. It uses the instance of [NavHostController] to navigate to any given composable
 * with the Navigation graph.
 *
 * @param[navHostController] an instance of [NavHostController] which is used to navigate to any composable
 * @param[viewHouseVM] An object of the [ViewHouseVM] which is used to encapsulate the data for the [ViewHouseScreen].
 * It is used to declare the behaviour of the children composable of this class
 * @param[housesViewModel] An object of the [HousesViewModel]which is used to encapsulate the data for the
 * [GetHousesScreen]. it is used to declare the behaviour of the children composable of this class
 * */
@Composable
fun  GoTNavHost(navHostController: NavHostController,
                viewHouseVM: ViewHouseVM = ViewHouseVM(),
                housesViewModel: HousesViewModel = HousesViewModel()
){
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

