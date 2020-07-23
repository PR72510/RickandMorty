package com.example.rickandmorty.network

import com.example.rickandmorty.util.BaseDataSource
import javax.inject.Inject

/**
 * Created by PR72510 on 23/7/20.
 */
class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
) : BaseDataSource() {

    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}