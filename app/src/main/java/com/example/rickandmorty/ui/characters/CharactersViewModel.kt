package com.example.rickandmorty.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.repository.CharacterRepository
import com.example.rickandmorty.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by PR72510 on 23/7/20.
 */

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()

    val characters: LiveData<Resource<List<Character>>>
        get() = _characters

    fun getAllCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters().collect {
                    _characters.postValue(it)
            }
        }
    }
}