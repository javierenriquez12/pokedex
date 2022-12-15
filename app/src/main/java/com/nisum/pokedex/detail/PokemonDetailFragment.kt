package com.nisum.pokedex.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nisum.domain.model.Pokemon
import com.nisum.pokedex.R
import com.nisum.pokedex.databinding.FragmentPokedexDetailBinding
import com.nisum.pokedex.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : DialogFragment() {

    private val viewModel: PokemonDetailViewModel by viewModels()
    private lateinit var binding: FragmentPokedexDetailBinding
    private lateinit var idPokemon: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreenDialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setIdPokemon()
        setUpPokemonImage()
        setUpPokemon()
        setUpBack()
        viewModel.fetchPokemon(idPokemon)
    }

    private fun setUpBack() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setIdPokemon() {
        idPokemon = arguments?.getString("id_pokemon") ?: ""
    }

    private fun setUpPokemon() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchDetailPokemon.collect { uiState ->
                    when (uiState) {
                        is PokemonDetailUiState.Success -> {
                            setDataPokemon(uiState.pokemon)
                        }
                        is PokemonDetailUiState.LoadingEncounter -> {

                        }
                        is PokemonDetailUiState.Error -> {

                        }
                        is PokemonDetailUiState.LoadingDetail -> {
                        }
                        is PokemonDetailUiState.LoadingEvolution -> {
                        }
                        is PokemonDetailUiState.SuccessEncounter -> {
                            bindingMovesPokemon(uiState.string)
                        }
                        is PokemonDetailUiState.SuccessEvolution -> {
                            bindingImagePokemonEvoluted(uiState.isEvolution)
                        }
                    }
                }
            }
        }
    }

    private fun bindingMovesPokemon(locations: List<String>) {
        binding.locationsPokemonTextView.movementMethod = ScrollingMovementMethod()
        if (locations.isNotEmpty())
            binding.locationsPokemonTextView.text =
                locations.joinToString("\n")
        else{
            binding.locationsPokemonTextView.visibility = View.GONE
            binding.locationsTitlePokemonTextView.visibility = View.GONE
        }

    }

    private fun setDataPokemon(pokemon: Pokemon) {
        binding.pokemonNameTextView.text = pokemon.name.replaceFirstChar { it.uppercaseChar() }
        binding.typePokemonTextView.text = "Type: ${pokemon.types[0]}"
        binding.abilitiesPokemonTextview.text = "Habilidades: ${pokemon.abilities.joinToString()}"
        binding.movesPokemonTextView.movementMethod = ScrollingMovementMethod()
        binding.movesPokemonTextView.text = pokemon.moves.joinToString("\n")
    }

    private fun bindingImagePokemonEvoluted(isEvolution: Boolean) {
        if (isEvolution) {
            Glide.with(requireContext())
                .load(
                    Utils.getUrlImagePokemon((idPokemon.toInt() + 1).toString())
                )
                .placeholder(android.R.color.transparent)
                .into(binding.evolutionPokemonImageView)
            binding.evolutionPokemonImageView.visibility = View.VISIBLE
            binding.evolutionTitleTextView.visibility = View.VISIBLE
        } else {
            binding.evolutionPokemonImageView.visibility = View.GONE
            binding.evolutionTitleTextView.visibility = View.GONE
        }
    }

    private fun setUpPokemonImage() {
        Glide.with(requireContext())
            .load(
                Utils.getUrlImagePokemon(idPokemon)
            )
            .placeholder(android.R.color.transparent)
            .into(binding.pokemonImageView)
    }
}