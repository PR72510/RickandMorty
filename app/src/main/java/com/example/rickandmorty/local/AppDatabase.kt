package com.example.rickandmorty.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmorty.models.Character

/**
 * Created by PR72510 on 24/7/20.
 */

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao() : CharacterDao

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
            Room.databaseBuilder(appContext, AppDatabase::class.java, "characters")
                .fallbackToDestructiveMigration()
                .build()
    }
}