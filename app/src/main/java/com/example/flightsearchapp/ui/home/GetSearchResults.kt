package com.example.flightsearchapp.ui.home

import androidx.compose.runtime.collectAsState
import com.example.flightsearchapp.data.airport.Airport
import com.example.flightsearchapp.data.airport.AirportsRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class GetSearchResults(private val airportsRepository: AirportsRepository) {

    sealed interface Result {
        object Error : Result
        data class Success(val airports: Flow<List<Airport>>) : Result
    }

    operator fun invoke(searchTerm: String): Result = try {
        val airports = airportsRepository.getAllAirports(searchTerm)
        Result.Success(airports = airports)
    } catch (e: IOException) {
        Result.Error
    }

    suspend fun getAirportByIataCode(iataCode: String): Flow<Airport> {
        return airportsRepository.getAirportByIataCode(iataCode)
    }
}