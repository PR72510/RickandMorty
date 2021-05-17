package com.example.rickandmorty.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.RemoteKeys

/**
 * Created by PR72510 on 24/7/20.
 */

@Database(
    entities = [Character::class, RemoteKeys::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "r_m_characters")
                .fallbackToDestructiveMigration()
                .build()
    }
}