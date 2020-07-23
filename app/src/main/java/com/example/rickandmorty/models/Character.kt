package com.example.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by PR72510 on 23/7/20.
 */
@Entity(tableName = "characters")
data class Character(
    val created: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)