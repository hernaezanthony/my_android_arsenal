package com.ahernaez.viewmodelwithstate.network

import com.ahernaez.viewmodelwithstate.models.Word
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("word")
    fun getRandomWord(): Single<List<Word>>

}