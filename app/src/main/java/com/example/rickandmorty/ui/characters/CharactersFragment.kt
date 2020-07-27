package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharacterClickListener {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpRecyclerView()
        handleObservers()
        viewModel.getAllCharacters()
    }

    private fun handleObservers() {
        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            when(characters){
                is Resource.Error ->{
                    Toast.makeText(requireContext(), characters.message, Toast.LENGTH_SHORT).show()
                    showProgress(false)
                }
                is Resource.Success -> {
                    binding.characterList = characters.data
                    showProgress(false)
                    binding.invalidateAll()
                }
                is Resource.Loading -> {
                    showProgress(true)
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.adapter = adapter
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible)
            binding.rlProgress.visibility = View.VISIBLE
        else
            binding.rlProgress.visibility = View.GONE
    }

    override fun onCharacterClicked(id: Int) {
        findNavController().navigate(R.id.action_charactersFragment_to_characterDetailFragment, bundleOf("id" to id))
    }
}