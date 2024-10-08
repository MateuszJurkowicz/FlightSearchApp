package com.example.flightsearchapp.data

import android.content.Context

interface AppContainer {
    val flightsRepository: AirportsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val flightsRepository: AirportsRepository by lazy {
        OfflineAirportsRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }
}
