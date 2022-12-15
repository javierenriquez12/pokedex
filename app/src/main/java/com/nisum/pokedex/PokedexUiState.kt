package com.nisum.pokedex

import com.nisum.domain.model.Result

sealed class PokedexUiState {
    data class Success(val pokemons: List<Result>) : PokedexUiState()
    data class Error(val exception: Exception) : PokedexUiState()
    data class Loading(val isLoad: Boolean) : PokedexUiState()

}
