package com.example.rickandmorty.ui.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        handleObservers()
        arguments?.getInt("id")?.let { viewModel .getCharacter(it)}
    }

    private fun handleObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer { character ->
            when(character){
                is Resource.Error ->{
                    Toast.makeText(requireContext(), character.message, Toast.LENGTH_SHORT).show()
                    showProgress(false)
                }
                is Resource.Success -> {
                    binding.character = character.data
                    showProgress(false)
                    binding.invalidateAll()
                }
                is Resource.Loading -> {
                    showProgress(true)
                }
            }
        })
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible)
            binding.rlProgress.visibility = View.VISIBLE
        else
            binding.rlProgress.visibility = View.GONE
    }

}