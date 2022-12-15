package com.nisum.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisum.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(private val pokemonUsecase: PokemonUseCase) :
    ViewModel() {

    private var _fetchPokemons: MutableStateFlow<PokedexUiState> = MutableStateFlow(
        PokedexUiState.Success(
            emptyList()
        )
    )
    val fetchPokemons: StateFlow<PokedexUiState> = _fetchPokemons

    fun fetchPokemons() {
        viewModelScope.launch {
            val result = with(Dispatchers.IO) {
                pokemonUsecase.fetchPokemons()
            }
            result.onStart {
                _fetchPokemons.value = PokedexUiState.Loading(true)
            }.catch { exception ->
                _fetchPokemons.value = PokedexUiState.Error(Exception(exception.message))
                exception.printStackTrace()
            }.collect {
                _fetchPokemons.value = PokedexUiState.Success(it.pokemonList)
            }
            _fetchPokemons.value = PokedexUiState.Loading(false)
        }
    }
}