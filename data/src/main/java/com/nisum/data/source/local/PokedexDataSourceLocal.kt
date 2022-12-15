package com.nisum.data.source.local

import com.nisum.data.source.local.entity.PokemonEntityDao
import javax.inject.Inject

class PokedexDataSourceLocal @Inject constructor(private val dao: PokedexDao) {

    suspend fun fetchPokemons() : List<PokemonEntityDao> = dao.fetchPokemons()

    suspend fun insertPokemons(pokemons: List<PokemonEntityDao>){
        dao.insertPokemons(pokemons)
    }

    suspend fun clear() {
        dao.clear()
    }
}