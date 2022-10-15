package com.ahernaez.viewmodelwithstate.views.main

import com.ahernaez.viewmodelwithstate.models.Word

sealed class MainState{

    object Loading: MainState()
    data class RandomWord(val data: List<Word>): MainState()
    data class Error(val errorString: String): MainState()
}
