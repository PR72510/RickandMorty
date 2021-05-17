package com.example.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.util.BindableAdapter

/**
 * Created by PR72510 on 26/7/20.
 */
class CharactersAdapter(private val listener: CharacterClickListener) :
    PagingDataAdapter<Character, CharactersViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it, listener) }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>(){
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}