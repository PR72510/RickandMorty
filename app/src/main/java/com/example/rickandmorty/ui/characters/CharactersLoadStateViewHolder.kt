package com.example.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.CharacterLoadStateFooterViewBinding

/**
 * Created by PR72510 on 17/05/21.
 */
class CharactersLoadStateViewHolder(
    private val binding: CharacterLoadStateFooterViewBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.errorMsg.isVisible = loadState is LoadState.Error
        binding.retryButton.isVisible = loadState is LoadState.Error

        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
            val binding = CharacterLoadStateFooterViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return CharactersLoadStateViewHolder(binding, retry)
        }
    }
}