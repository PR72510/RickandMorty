package com.example.rickandmorty.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.repository.CharacterRepository

/**
 * Created by PR72510 on 23/7/20.
 */

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

}