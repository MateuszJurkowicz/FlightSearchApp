package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE iata_code LIKE '%' || :inputText || '%' OR name LIKE '%' || :inputText || '%'")
    fun getAllAirports(inputText: String): Flow<List<Airport>>
}
