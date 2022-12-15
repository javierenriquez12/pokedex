package com.nisum.data.mapper

import com.nisum.data.entity.PokedexEntity
import com.nisum.data.entity.pokemon.PokemonEntity
import com.nisum.data.source.local.entity.PokemonEntityDao
import com.nisum.domain.model.Pokedex
import com.nisum.domain.model.Pokemon
import com.nisum.domain.model.Result

object PokemonMapper {
    fun PokedexEntity.listToDomain() = Pokedex(
        count = this.count,
        next = this.next,
        previous = this.previous,
        pokemonList = this.results.mapIndexed { index, it ->
            Result(
                (index + 1).toString(),
                it.name,
                it.url
            )
        }
    )

    fun PokemonEntity.toDomain() = Pokemon(
        abilities = this.abilities.map { it.ability.name },
        types = this.types.map { it.type.name },
        moves = this.moves.map { it.move.name },
        speciesUrl = this.species.url,
        locationEncounter = this.location_area_encounters,
        name = this.name
    )

    fun PokedexEntity.listToLocal() =
        this.results.mapIndexed { index, it ->
            PokemonEntityDao(
                index.toString(),
                it.name,
                it.url
            )
        }

    fun listToDomain(pokemonList: List<PokemonEntityDao>) =
        Pokedex(
            pokemonList = pokemonList.mapIndexed { index, it ->
                Result(
                    (index + 1).toString(),
                    it.name,
                    it.url
                )
            }
        )
}