package com.nisum.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nisum.domain.model.Result
import com.nisum.pokedex.databinding.ItemPokedexBinding
import com.nisum.pokedex.util.Utils.getUrlImagePokemon

class PokedexAdapter(
    internal val listPokemon: List<Result>,
    private val actionNextFragment: (id: String) -> Unit
) :
    RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {


    class PokemonViewHolder(val binding: ItemPokedexBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokedexBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        with(holder) {
            with(listPokemon[position]) {
                itemView.setOnClickListener { actionNextFragment.invoke(id) }
                binding.appCompatTextView.text = this.name.replaceFirstChar { it.uppercaseChar() }
                Glide.with(holder.itemView.context)
                    .load(
                        getUrlImagePokemon(id)
                    )
                    .placeholder(android.R.color.transparent)
                    .into(binding.pokemonImageView)
            }
        }
    }

    override fun getItemCount(): Int = listPokemon.size
}