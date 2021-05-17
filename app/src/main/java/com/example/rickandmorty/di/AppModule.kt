package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.local.AppDatabase
import com.example.rickandmorty.network.CharacterRemoteDataSource
import com.example.rickandmorty.network.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by PR72510 on 23/7/20.
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) =
        CharacterRemoteDataSource(characterService)

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

}