package com.nisum.data.mapper

import com.nisum.data.entity.pokemon.EncounterEntity
import com.nisum.domain.model.Encounter

object PokemonEncounterMapper {

    fun encounterEntityToDomain(listEncounter: List<EncounterEntity>): Encounter {
        return Encounter(nameEncounters = listEncounter.map {
            it.location_area.name
        })
    }
}