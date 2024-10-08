package com.example.flightsearchapp

import androidx.compose.runtime.Composable
import com.example.flightsearchapp.ui.navigation.FlightSearchNavGraph

@Composable
fun FlightSearchApp(navController: NavHostController = rememberNavController()) {
    FlightSearchNavGraph(navController = navController)
}