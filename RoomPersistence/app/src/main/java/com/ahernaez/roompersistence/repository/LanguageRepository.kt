package com.ahernaez.roompersistence.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.ahernaez.roompersistence.model.Language
import com.ahernaez.roompersistence.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object LanguageRepository {

    var appDatabase: AppDatabase?  = null

    private fun initializeDb(context: Context): AppDatabase{
        return AppDatabase.getDatabaseClient(context)
    }

    fun getLanguageList(context: Context): LiveData<List<Language>>{

        appDatabase = initializeDb(context)
        return appDatabase!!.languageDao().getLanguageList()
    }

    fun insertLanguage(context: Context, language: Language){

        appDatabase = initializeDb(context)

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase!!.languageDao().insertLanguage(language)
        }
    }
}