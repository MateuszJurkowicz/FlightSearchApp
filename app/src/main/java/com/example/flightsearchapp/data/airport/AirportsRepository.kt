package com.example.flightsearchapp.data.airport

import kotlinx.coroutines.flow.Flow

interface AirportsRepository {
    fun getAllAirports(searchTerm: String): Flow<List<Airport>>
    fun getAirportByIataCode(iataCode: String): Flow<Airport>
}
