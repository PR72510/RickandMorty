package com.example.rickandmorty.ui.characterdetail

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
 * Created by PR72510 on 27/7/20.
 */
class CharacterDetailViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _character = MutableLiveData<Resource<Character>>()

    val character: LiveData<Resource<Character>>
        get() = _character

    fun getCharacter(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacter(id).collect {
                _character.postValue(it)
            }
        }
    }
}