package com.example.flightsearchapp.data.airport

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(private val airportDao: AirportDao) : AirportsRepository {
    override fun getAllAirports(searchTerm: String): Flow<List<Airport>> = airportDao.getAllAirports(searchTerm)
    override fun getAirportByIataCode(iataCode: String): Flow<Airport> = airportDao.getAirportByIataCode(iataCode)
}
