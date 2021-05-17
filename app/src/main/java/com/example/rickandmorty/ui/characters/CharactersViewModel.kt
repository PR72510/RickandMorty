package com.example.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by PR72510 on 23/7/20.
 */

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private var currentSearchResult: Flow<PagingData<Character>>? = null

    fun getAllCharacters(): Flow<PagingData<Character>> {
        val lastResult = currentSearchResult
        if(lastResult != null)
            return lastResult

        val newResult = repository.getCharacterResultStream().cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }
}