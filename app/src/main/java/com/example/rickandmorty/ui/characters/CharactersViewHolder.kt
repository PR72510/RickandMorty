package com.example.rickandmorty.ui.characters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.Character

/**
 * Created by PR72510 on 26/7/20.
 */
class CharactersViewHolder(val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        character: Character,
        listener: CharacterClickListener
    ) {
        binding.character = character
        binding.root.setOnClickListener { listener.onCharacterClicked(character.id) }
    }
}