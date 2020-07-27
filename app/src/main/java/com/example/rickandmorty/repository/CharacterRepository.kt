package com.example.rickandmorty.repository

import android.content.Context
import com.example.rickandmorty.local.CharacterDao
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.network.CharacterRemoteDataSource
import com.example.rickandmorty.util.AppUtils
import com.example.rickandmorty.util.Resource
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by PR72510 on 23/7/20.
 */
class CharacterRepository @Inject constructor(
    @ActivityContext val context: Context,
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    suspend fun getCharacters(): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Loading())
            if (AppUtils.isInternetAvailable(context)) {
                val remoteData = remoteDataSource.getCharacters()
                if (remoteData is Resource.Success) {
                    localDataSource.insertAll(remoteData.data!!.results)
                    emit(Resource.Success(localDataSource.getAllCharacters()))
                } else if (remoteData is Resource.Error) {
                    emit(Resource.Error(remoteData.message!!))
                }
            } else {
                val localData = localDataSource.getAllCharacters()
                emit(Resource.Success(localData))
            }
        }
    }

    suspend fun getCharacter(id: Int): Flow<Resource<Character>> {
        return flow {
            emit(Resource.Loading())
            if (AppUtils.isInternetAvailable(context)) {
                val remoteData = remoteDataSource.getCharacter(id)
                if (remoteData is Resource.Success) {
                    localDataSource.insert(remoteData.data!!)
                    emit(Resource.Success(localDataSource.getCharacter(id)))
                } else if (remoteData is Resource.Error) {
                    emit(Resource.Error(remoteData.message!!))
                }
            } else {
                val localData = localDataSource.getCharacter(id)
                emit(Resource.Success(localData))
            }
        }
    }
}