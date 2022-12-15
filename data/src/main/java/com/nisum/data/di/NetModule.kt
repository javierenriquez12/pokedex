package com.nisum.data.di

import com.nisum.data.repository.PokemonRepositoryImpl
import com.nisum.data.source.remote.PokedexDataSourceRemote
import com.nisum.data.source.remote.PokedexDataSourceRemoteImpl
import com.nisum.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NetModule {

    @Binds
    abstract fun bindsPokemonDataSource(
        pokemonDataSourceRemoteImpl: PokedexDataSourceRemoteImpl
    ): PokedexDataSourceRemote

    @Binds
    abstract fun bindsPokemonRepository(
        pokemonRepository: PokemonRepositoryImpl
    ): PokemonRepository
}