package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(private val airportDao: AirportDao) : AirportsRepository {
    override fun getAllAirports(searchTerm: String): Flow<List<Airport>> = airportDao.getAllAirports(searchTerm)

}
