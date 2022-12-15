package com.nisum.domain.repository

import com.nisum.domain.model.Encounter
import com.nisum.domain.model.Pokedex
import com.nisum.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemons() : Flow<Pokedex>
    suspend fun fetchPokemon(pokemonId: String) : Flow<Pokemon>
    suspend fun fetchEncounterPokemon(pokemonId: String) : Flow<Encounter>
    suspend fun fetchEvolutionPokemon(pokemonId: String) : Flow<Boolean>
}