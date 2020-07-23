package com.example.rickandmorty.network

import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by PR72510 on 23/7/20.
 */
interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int): Response<Character>
}