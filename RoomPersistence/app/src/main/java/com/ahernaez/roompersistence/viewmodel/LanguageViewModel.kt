package com.ahernaez.roompersistence.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahernaez.roompersistence.model.Language
import com.ahernaez.roompersistence.repository.LanguageRepository

class LanguageViewModel: ViewModel() {

    var languageListLiveData: LiveData<List<Language>>? = null

    fun getLanguageList(context: Context): LiveData<List<Language>>?{

        languageListLiveData = LanguageRepository.getLanguageList(context)
        return languageListLiveData
    }

    fun insertLanguage(context: Context, language: Language){

        LanguageRepository.insertLanguage(context, language)
    }

    fun updateLanguage(context: Context, language: Language){

        LanguageRepository.updateLanguage(context, language)
    }

    fun deleteLanguage(context: Context, languageId: Int){

        LanguageRepository.deleteLanguage(context, languageId)
    }
}