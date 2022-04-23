package com.ahernaez.roompersistence.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahernaez.roompersistence.model.Language

@Database(entities = [Language::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun languageDao(): LanguageDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabaseClient(context: Context): AppDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this){

                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "APP_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}