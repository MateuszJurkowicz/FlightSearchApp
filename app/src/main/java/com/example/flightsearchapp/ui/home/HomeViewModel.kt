package com.example.flightsearchapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.AirportsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class HomeViewModel(private val getSearchResults: GetSearchResults) : ViewModel() {

    sealed interface HomeUiState {
        object IdleScreen : HomeUiState
        object Loading : HomeUiState
        object Error : HomeUiState
        object NoResults : HomeUiState
        data class SearchResultsFetched(val airports: Flow<List<Airport>>) : HomeUiState
    }

    sealed interface SearchFieldState {
        object Idle : SearchFieldState //bezczynny
        object EmptyActive : SearchFieldState
        object WithInputActive : SearchFieldState
    }


    private val _searchFieldState: MutableStateFlow<SearchFieldState> =
        MutableStateFlow(SearchFieldState.Idle)
    val searchFieldState: StateFlow<SearchFieldState> = _searchFieldState

    private val _homeUiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.IdleScreen)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val _inputText: MutableStateFlow<String> = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val isInitialized = AtomicBoolean(false)

    @OptIn(FlowPreview::class)
    fun initialize() {
        if (isInitialized.compareAndSet(false, true)) {
            viewModelScope.launch {
                inputText.debounce(timeoutMillis = TIMEOUT_MILLIS).collectLatest { input ->
                    if (input.blankOrEmpty()) {
                        _homeUiState.update { HomeUiState.IdleScreen }
                        return@collectLatest
                    }
                    when (val result = getSearchResults(input)) {
                        is GetSearchResults.Result.Success -> {
                            _homeUiState.update { HomeUiState.SearchResultsFetched(result.airports) }
                            // Check for empty results after emitting SearchResultsFetched
                            result.airports.collect { airports ->
                                if (airports.isEmpty()) {
                                    _homeUiState.update { HomeUiState.NoResults }
                                }
                            }
                        }

                        is GetSearchResults.Result.Error -> {
                            _homeUiState.update { HomeUiState.Error }
                        }
                    }
                }
            }
        }
    }


    fun updateInput(inputText: String) {
        _inputText.update { inputText }
        activateSearchField()

        if (inputText.blankOrEmpty().not()) {
            _homeUiState.update { HomeUiState.Loading }
        }
    }

    fun searchFieldActivated() {
        activateSearchField()
    }

    fun revertToInitialState() {
        _inputText.update { "" }
        _searchFieldState.update { SearchFieldState.Idle }
    }

    fun clearInput() {
        _inputText.update { "" }
        _searchFieldState.update { SearchFieldState.EmptyActive }
    }

    private fun activateSearchField() {
        if (inputText.value.blankOrEmpty().not()) {
            _searchFieldState.update { SearchFieldState.WithInputActive }
        } else {
            _searchFieldState.update { SearchFieldState.EmptyActive }
        }
    }

    private fun String.blankOrEmpty() = this.isBlank() || this.isEmpty()


    companion object {
        private const val TIMEOUT_MILLIS = 5_00L
    }


}
