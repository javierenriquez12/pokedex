package com.nisum.data.source.remote

import com.nisum.data.entity.pokemon.EncounterEntity
import com.nisum.data.entity.PokedexEntity
import com.nisum.data.entity.pokemon.PokemonEntity
import com.nisum.data.entity.pokemon.SpeciesDetailEntity
import kotlinx.coroutines.flow.Flow

interface PokedexDataSourceRemote {
    suspend fun fetchPokemons(): Flow<PokedexEntity>
    suspend fun fetchPokemon(pokemonId: String): Flow<PokemonEntity>
    suspend fun fetchEncounterPokemon(pokemonId: String) : Flow<List<EncounterEntity>>
    suspend fun fetchSpeciesPokemon(pokemonId: String) : Flow<SpeciesDetailEntity>
}