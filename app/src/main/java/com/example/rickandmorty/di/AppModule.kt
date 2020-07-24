package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.local.AppDatabase
import com.example.rickandmorty.local.CharacterDao
import com.example.rickandmorty.network.CharacterRemoteDataSource
import com.example.rickandmorty.network.CharacterService
import com.example.rickandmorty.repository.CharacterRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by PR72510 on 23/7/20.
 */

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesCharacterService(retrofit: Retrofit) = retrofit.create(CharacterService::class.java)

    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) =
        CharacterRemoteDataSource(characterService)

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

//    @Provides
//    fun provideRepository(
//        @ActivityContext context: Context,
//        remoteDataSource: CharacterRemoteDataSource,
//        localDataSource: CharacterDao
//    ) = CharacterRepository(context, remoteDataSource, localDataSource)

}