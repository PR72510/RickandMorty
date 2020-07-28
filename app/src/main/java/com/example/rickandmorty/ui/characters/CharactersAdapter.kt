package com.example.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.util.BindableAdapter

/**
 * Created by PR72510 on 26/7/20.
 */
class CharactersAdapter(val listener: CharacterClickListener) :
    RecyclerView.Adapter<CharactersViewHolder>(), BindableAdapter<List<Character>> {
    var characterList = emptyList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount() = characterList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characterList[position], listener)
    }

    override fun setData(data: List<Character>) {
        characterList = data
        notifyDataSetChanged()
    }
}