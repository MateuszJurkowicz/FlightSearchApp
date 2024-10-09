package com.example.flightsearchapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.ui.AppViewModelProvider
import com.example.flightsearchapp.ui.components.FlightSearchTopAppBar
import com.example.flightsearchapp.ui.components.SearchInputField
import com.example.flightsearchapp.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val searchFieldState by viewModel.searchFieldState.collectAsState()
    val inputText by viewModel.inputText.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(null) {
        viewModel.initialize()
    }

    SearchScreenLayout(
        viewState = homeUiState,
        searchFieldState = searchFieldState,
        inputText = inputText,
        onSearchInputChanged = { input -> viewModel.updateInput(input) },
        onSearchInputClicked = { viewModel.searchFieldActivated() },
        onClearInputClicked = { viewModel.clearInput() },
        onChevronClicked = {
            viewModel.revertToInitialState()
            keyboardController?.hide()
        },
        onItemClicked = {
            viewModel.onItemClicked(
                Airport(
                    id = it.id,
                    name = it.name,
                    iataCode = it.iataCode,
                    passengers = it.passengers
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenLayout(
    viewState: HomeViewModel.HomeUiState,
    searchFieldState: HomeViewModel.SearchFieldState,
    inputText: String,
    onSearchInputChanged: (String) -> Unit,
    onSearchInputClicked: () -> Unit,
    onClearInputClicked: () -> Unit,
    onChevronClicked: () -> Unit,
    onItemClicked: (Airport) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SearchInputField(
                searchFieldState = searchFieldState,
                inputText = inputText,
                onClearInputClicked = onClearInputClicked,
                onSearchInputChanged = onSearchInputChanged,
                onClicked = onSearchInputClicked,
                onChevronClicked = onChevronClicked,
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (viewState) {
                is HomeViewModel.HomeUiState.IdleScreen -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.undraw_search),
                            contentDescription = "Illustration",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is HomeViewModel.HomeUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is HomeViewModel.HomeUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error :(", color = MaterialTheme.colorScheme.error)
                    }
                }

                is HomeViewModel.HomeUiState.NoResults -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No results for this input :(")
                    }
                }

                is HomeViewModel.HomeUiState.SearchResultsFetched -> {
                    val airports by viewState.airports.collectAsState(initial = emptyList()) // Collect the flow
                    SuggestionsBody(
                        airports = airports, // Pass the collected list
                        modifier = Modifier.fillMaxSize(),
                        onItemClicked = onItemClicked
                    )
                }
                is HomeViewModel.HomeUiState.SearchResultClicked -> {
                    val flightsTo by viewState.flightsTo.collectAsState(initial = emptyList()) // Collect the flow
                    val flightsFrom = viewState.flightsFrom
                    FlightsBody(
                        flightsFrom = flightsFrom, // Pass the collected list
                        flightsTo = flightsTo,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyPreview() {
    SuggestionsBody(listOf(), onItemClicked = {}, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Column {
        SuggestionsBody(
            listOf(
                Airport(1, "Warsaw Airport", "COA", 911),
                Airport(2, "Decatur Central Airport", "DEC", 855)
            ), Modifier,
            onItemClicked = {}
        )
    }
}
