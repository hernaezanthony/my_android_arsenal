package com.ahernaez.viewmodelwithstate.views.main

import com.ahernaez.viewmodelwithstate.models.Word
import com.ahernaez.viewmodelwithstate.network.RetrofitBuilder
import io.reactivex.Single

object MainRepository {

    fun getRandomWord(): Single<List<Word>> {
        return RetrofitBuilder.apiService.getRandomWord()
    }
}