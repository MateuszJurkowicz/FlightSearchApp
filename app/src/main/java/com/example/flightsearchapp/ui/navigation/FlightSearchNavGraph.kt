package com.example.flightsearchapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FlightSearchScreen.Start.name,
        modifier = modifier
    ) {
        composable(route = FlightSearchScreen.Start.name) {
            HomeScreen(

            )
        }
    }
}
