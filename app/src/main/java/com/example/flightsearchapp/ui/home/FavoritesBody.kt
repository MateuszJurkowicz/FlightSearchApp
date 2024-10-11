package com.example.flightsearchapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.airport.Airport
import com.example.flightsearchapp.data.favorite.Favorite

@Composable
fun FavoritesBody(
    favorites: List<Favorite>,
    onFavoriteClicked: (Airport, Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    Column()
    {
        Text(
            text = "Favorite routes",
            modifier = Modifier
                .padding(start = 16.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(items = favorites, key = { it.id }) { favorite ->
//                FlightCard(
//                    flightFrom = favorite.departureCode,
//                    flightTo = favorite.arrivalCode,
//                    onFavoriteClicked = onFavoriteClicked
//                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavoritesBodyPreview() {
    FavoritesBody(
        listOf(
            Favorite(1, "BCO", "DSA"),
            Favorite(2, "DSA", "BCO")
        ),
        { _, _ -> },
    )
}