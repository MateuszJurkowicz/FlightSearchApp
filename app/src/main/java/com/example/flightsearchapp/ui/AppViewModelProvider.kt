package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightSearchApplication
import com.example.flightsearchapp.ui.home.GetSearchResults
import com.example.flightsearchapp.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(GetSearchResults(flightSearchApplication().container.flightsRepository))
        }
    }
}

fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)