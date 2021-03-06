package com.example.rickandmorty.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.models.Character

/**
 * Created by PR72510 on 24/7/20.
 */
@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int,Character>

    @Query("SELECT * FROM characters where id= :id")
    suspend fun getCharacter(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterList: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}