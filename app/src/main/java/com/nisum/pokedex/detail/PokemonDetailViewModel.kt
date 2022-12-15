package com.nisum.pokedex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisum.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonUsecase: PokemonUseCase) :
    ViewModel() {

    private var _fetchDetailPokemon: MutableStateFlow<PokemonDetailUiState> = MutableStateFlow(
        PokemonDetailUiState.LoadingDetail(
            true
        )
    )
    val fetchDetailPokemon: StateFlow<PokemonDetailUiState> = _fetchDetailPokemon

    fun fetchPokemon(idPokemon: String) {
        viewModelScope.launch {
            val result = with(Dispatchers.IO) {
                pokemonUsecase.fetchPokemon(idPokemon)
            }
            result.collect {
                fetchAreaEncounter(idPokemon)
                fetchEvolution(idPokemon)
                _fetchDetailPokemon.value = PokemonDetailUiState.Success(it)
            }
        }
    }

    private fun fetchAreaEncounter(idPokemon: String) {
        viewModelScope.launch {
            val result = with(Dispatchers.IO) {
                pokemonUsecase.fetchEncounterPokemon(idPokemon)
            }
            result.collect {
                _fetchDetailPokemon.value = PokemonDetailUiState.LoadingEncounter(false)
                _fetchDetailPokemon.value = PokemonDetailUiState.SuccessEncounter(it.nameEncounters)
            }
        }
    }

    private fun fetchEvolution(idPokemon: String) {
        viewModelScope.launch {
            val result = with(Dispatchers.IO) {
                pokemonUsecase.fetchEvolutionPokemon(idPokemon)
            }
            result.collect {
                _fetchDetailPokemon.value = PokemonDetailUiState.LoadingEvolution(false)
                _fetchDetailPokemon.value = PokemonDetailUiState.SuccessEvolution(it)
            }
        }
    }
}