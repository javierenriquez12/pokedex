package com.nisum.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nisum.pokedex.databinding.ActivityPokedexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokedexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
