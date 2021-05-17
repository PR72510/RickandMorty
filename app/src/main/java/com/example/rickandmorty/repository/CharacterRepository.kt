package com.example.rickandmorty.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.local.CharacterDao
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.network.CharacterRemoteDataSource
import com.example.rickandmorty.network.CharacterService
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
//    @ActivityContext val context: Context,
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao,
    private val service: CharacterService,
    private val characterRemoteMediator: CharactersRemoteMediator
) {

    fun getCharacterResultStream(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { localDataSource.getAllCharacters() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = characterRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getCharacter(id: Int): Flow<Resource<Character>> {
        return flow {
            emit(Resource.Loading())
//            if (AppUtils.isInternetAvailable(context)) {
                val remoteData = remoteDataSource.getCharacter(id)
            when (remoteData) {
                is Resource.Success -> {
                    localDataSource.insert(remoteData.data!!)
                    emit(Resource.Success(localDataSource.getCharacter(id)))
                }
                is Resource.Error -> {
                    emit(Resource.Error(remoteData.message!!))
                }
                //            }
                else -> {
                    val localData = localDataSource.getCharacter(id)
                    emit(Resource.Success(localData))
                }
            }
        }
    }
}