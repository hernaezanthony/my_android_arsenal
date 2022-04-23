package com.ahernaez.roompersistence.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.ahernaez.roompersistence.model.Language

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: Language)

    @Transaction
    fun getLanguageList(): LiveData<List<Language>>

}