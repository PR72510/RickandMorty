package com.example.rickandmorty.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.local.AppDatabase
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.RemoteKeys
import com.example.rickandmorty.network.CharacterService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by PR72510 on 13/05/21.
 */

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator @Inject constructor(
    private val service: CharacterService,
    private val database: AppDatabase
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val response = service.getAllCharacters(page)

            val characters = response.results
            val endOfPagination = characters.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.characterDao().clearCharacters()
                    database.remoteKeyDao().clearRemoteKeys()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = characters.map { character ->
                    RemoteKeys(
                        character.id,
                        prevKey,
                        nextKey
                    )
                }
                database.characterDao().insertAll(characters)
                database.remoteKeyDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPagination)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            database.remoteKeyDao().getKeyFromCharacter(it.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            database.remoteKeyDao().getKeyFromCharacter(it.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Character>):
            RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                database.remoteKeyDao().getKeyFromCharacter(it.id)
            }
        }
    }
}