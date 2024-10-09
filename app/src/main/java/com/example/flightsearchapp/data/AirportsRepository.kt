package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface AirportsRepository {

    fun getAllAirports(searchTerm: String): Flow<List<Airport>>

}
