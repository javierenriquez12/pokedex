package com.nisum.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.nisum.pokedex.databinding.FragmentPokedexBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.nisum.domain.model.Result

@AndroidEntryPoint
class PokedexFragment : Fragment() {
    private lateinit var binding: FragmentPokedexBinding
    private val pokedexViewModel: PokedexViewModel by viewModels()
    private lateinit var adapter: PokedexAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPokemons()
        setUpListPokemons()
        requestSearch()
    }

    private fun requestSearch() {
        binding.pokemonSearchView.requestFocus()
    }

    private fun setUpSearchPokemon() {
        val listFilter = adapter.listPokemon
        binding.pokemonSearchView.setOnQueryTextListener(
            object : OnQueryTextListener{
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    setUpRecyclerPokemons(
                        listFilter.filter {
                            it.name.startsWith(text?:"")
                        }
                    )
                    return false
                }
            }
        )
    }

    private fun loadPokemons() {
        pokedexViewModel.fetchPokemons()
    }

    private fun setUpRecyclerPokemons(listPokemon: List<Result>) {
        this.adapter = PokedexAdapter(listPokemon, ::actionDetailPokemonFragment)
        binding.pokemonRecyclerView.apply {
            adapter = this@PokedexFragment.adapter
        }
    }

    private fun actionDetailPokemonFragment(idPokemon: String){
        val action = PokedexFragmentDirections.actionPokedexFragmentToPokemonDetailFragment(
            idPokemon
        )
        findNavController().navigate(action)
    }

    private fun setUpListPokemons() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokedexViewModel.fetchPokemons.collect { uiState ->
                    when (uiState) {
                        is PokedexUiState.Success -> {
                            setUpRecyclerPokemons(uiState.pokemons)
                            setUpSearchPokemon()
                        }
                        is PokedexUiState.Error -> {

                        }
                        is PokedexUiState.Loading -> {
                            if(uiState.isLoad){
                                binding.pokemonProgressCircle.visibility = View.VISIBLE
                                binding.pokemonRecyclerView.visibility = View.GONE
                            } else {
                                binding.pokemonProgressCircle.visibility = View.GONE
                                binding.pokemonRecyclerView.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}
