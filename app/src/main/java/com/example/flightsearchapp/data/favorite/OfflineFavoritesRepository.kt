package com.example.flightsearchapp.data.favorite

import kotlinx.coroutines.flow.Flow

class OfflineFavoritesRepository(private val favoriteDao: FavoriteDao) : FavoritesRepository {
    override fun getAllFavorites(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()
    override suspend fun insertFavorite(favorite: Favorite) =
        favoriteDao.insertFavorite(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)
    override fun getFavoriteByIataCode(departureCode: String, destinationCode: String): Flow<Favorite> =
        favoriteDao.getFavoriteByIataCode(departureCode, destinationCode)
}