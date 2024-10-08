package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface AirportsRepository {

    fun getAllAirports(): Flow<List<Airport>>

}
