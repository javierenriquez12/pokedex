package com.nisum.data.repository

import com.nisum.data.mapper.PokemonEncounterMapper
import com.nisum.data.mapper.PokemonEvolutionMapper.toDomain
import com.nisum.data.mapper.PokemonMapper.listToDomain
import com.nisum.data.mapper.PokemonMapper.listToLocal
import com.nisum.data.mapper.PokemonMapper.toDomain
import com.nisum.data.source.local.PokedexDataSourceLocal
import com.nisum.data.source.remote.PokedexDataSourceRemote
import com.nisum.domain.model.Encounter
import com.nisum.domain.model.Pokedex
import com.nisum.domain.model.Pokemon
import com.nisum.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSourceLocal: PokedexDataSourceLocal,
    private val pokemonDataSourceRemote: PokedexDataSourceRemote,
) : PokemonRepository {

    override suspend fun fetchPokemons(): Flow<Pokedex> {
        return pokemonDataSourceRemote.fetchPokemons()
            .onEach {
                pokemonDataSourceLocal.insertPokemons(pokemons = it.listToLocal())
            }.map {
                it.listToDomain()
            }.catch {
                emit(listToDomain(pokemonDataSourceLocal.fetchPokemons()))
            }
    }

    override suspend fun fetchPokemon(pokemonId: String): Flow<Pokemon> {
        return pokemonDataSourceRemote.fetchPokemon(pokemonId).map {
            it.toDomain()
        }
    }

    override suspend fun fetchEncounterPokemon(pokemonId: String): Flow<Encounter> {
        return pokemonDataSourceRemote.fetchEncounterPokemon(pokemonId).map {
            PokemonEncounterMapper.encounterEntityToDomain(it)
        }
    }

    override suspend fun fetchEvolutionPokemon(pokemonId: String): Flow<Boolean> {
        return pokemonDataSourceRemote.fetchSpeciesPokemon(pokemonId).map {
            it.toDomain()
        }
    }
}