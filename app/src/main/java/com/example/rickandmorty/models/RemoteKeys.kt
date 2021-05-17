package com.example.rickandmorty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by PR72510 on 13/05/21.
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)