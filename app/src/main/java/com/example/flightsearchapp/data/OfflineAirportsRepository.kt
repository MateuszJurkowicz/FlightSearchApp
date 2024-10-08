package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(private val airportDao: AirportDao) : AirportsRepository {
    override fun getAllAirports(): Flow<List<Airport>> = airportDao.getAllAirports()

}
