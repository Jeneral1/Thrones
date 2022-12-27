package com.zatec.thrones

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ThronesNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navHostController: NavHostController

    @Before
    fun setThronesNavHost(){
        composeTestRule.setContent {
            navHostController = rememberNavController()
            GoTNavHost(navHostController = navHostController)
        }
    }

    @Test
    fun gotNavHost(){
        composeTestRule.onNodeWithContentDescription("GoT Houses Screen").assertIsDisplayed()
    }

    @Test
    fun gotNavHost_navigateToViewHouseScreen(){

        runBlocking {
            withContext(Dispatchers.Main){
                navHostController.navigate("${GoTScreen.ViewHouse.name}/10")
            }
        }

        composeTestRule.onRoot(false).printToLog("THIs is GroOt")
        composeTestRule.onNodeWithContentDescription("View House Screen").assertIsDisplayed()
    }
}