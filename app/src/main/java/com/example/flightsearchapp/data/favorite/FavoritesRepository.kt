package com.example.flightsearchapp.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    fun getFavoriteByIataCode(departureCode: String, destinationCode: String): Flow<Favorite>

}