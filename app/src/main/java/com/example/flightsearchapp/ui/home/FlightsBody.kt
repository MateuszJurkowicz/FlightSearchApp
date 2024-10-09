package com.example.flightsearchapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.ui.theme.color_dark_silver
import com.example.flightsearchapp.ui.theme.color_silver
import com.example.flightsearchapp.ui.theme.color_soft_silver

@Composable
fun FlightsBody(flightsFrom: Airport, flightsTo: List<Airport>, modifier: Modifier = Modifier) {
    Column()
    {
        Text(
            text = "Flights from ${flightsFrom.iataCode}",
            modifier = Modifier
                .padding(start = 16.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            items(items = flightsTo, key = { it.id }) { flightTo ->
                FlightCard(
                    flightFrom = flightsFrom,
                    flightTo = flightTo
                )
            }
        }
    }
}

@Composable
fun FlightCard(flightFrom: Airport, flightTo: Airport) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small),
        colors = CardDefaults.cardColors(
            containerColor = color_silver
        )
    )
    {
        Row(modifier = Modifier.fillMaxWidth())
        {
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)
            )
            {
                Text(
                    text = stringResource(id = R.string.depart),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Normal
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        text = flightFrom.iataCode,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = flightFrom.name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.arrive),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Normal
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = flightTo.iataCode,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = flightTo.name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            )
            {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.favorite_button),
                        tint = color_dark_silver,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightsBodyPreview() {
    FlightsBody(
        flightsFrom = Airport(1, "Warsaw Airport", "COA", 911),
        flightsTo = listOf(
            Airport(2, "Decatur Central Airport", "DEC", 855),
            Airport(3, "Indonesia International Airport", "IND", 855),
        )
    )
}