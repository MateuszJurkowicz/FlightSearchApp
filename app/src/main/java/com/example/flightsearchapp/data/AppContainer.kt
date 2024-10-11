package com.example.flightsearchapp.data

import android.content.Context
import com.example.flightsearchapp.data.airport.AirportsRepository
import com.example.flightsearchapp.data.airport.OfflineAirportsRepository
import com.example.flightsearchapp.data.favorite.FavoritesRepository
import com.example.flightsearchapp.data.favorite.OfflineFavoritesRepository

interface AppContainer {
    val flightsRepository: AirportsRepository
    val favoritesRepository: FavoritesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val flightsRepository: AirportsRepository by lazy {
        OfflineAirportsRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }
    override val favoritesRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}
