package com.ahernaez.retrofitsample.api

import com.ahernaez.retrofitsample.model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    fun getPhotoList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<List<Photo>>
}