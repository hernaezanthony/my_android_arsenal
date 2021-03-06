package com.ahernaez.roompersistence.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahernaez.roompersistence.model.Language

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: Language)

    @Transaction
    @Query("SELECT * FROM Language")
    fun getLanguageList(): LiveData<List<Language>>

    @Update
    fun updateLanguage(language: Language)

    @Transaction
    @Query("DElETE FROM Language WHERE language_id = :languageId")
    fun deleteLanguage(languageId: Int)

}