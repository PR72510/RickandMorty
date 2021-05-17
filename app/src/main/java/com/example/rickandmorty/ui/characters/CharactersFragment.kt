package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharacterClickListener {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
        setUpRecyclerView()
        handleObservers()
    }

    private fun handleObservers() {
        lifecycleScope.launch {
            viewModel.getAllCharacters().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.rvCharacters.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            showProgress(loadState.mediator?.refresh is LoadState.Loading)
            binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.rvCharacters.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.rvCharacters.visibility = View.VISIBLE
        }
    }
}