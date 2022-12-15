package com.nisum.data.mapper

import com.nisum.data.entity.pokemon.EncounterEntity
import com.nisum.data.entity.pokemon.SpeciesDetailEntity
import com.nisum.data.entity.pokemon.SpeciesEntity
import com.nisum.domain.model.Encounter

object PokemonEvolutionMapper {

    fun SpeciesDetailEntity.toDomain() = !this.forms_switchable
}