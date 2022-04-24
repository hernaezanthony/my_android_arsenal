package com.ahernaez.roompersistence.repository

import android.app.Activity
import android.content.Context
import android.widget.Toast
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

    fun deleteLanguage(context: Context, languageId: Int){

        appDatabase = initializeDb(context)

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase!!.languageDao().deleteLanguage(languageId)

            (context as Activity).runOnUiThread{
                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}