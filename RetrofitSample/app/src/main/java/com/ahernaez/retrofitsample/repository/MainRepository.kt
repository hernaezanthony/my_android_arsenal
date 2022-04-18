package com.ahernaez.retrofitsample.repository

import com.ahernaez.retrofitsample.api.RetrofitBuilder
import com.ahernaez.retrofitsample.model.Photo
import io.reactivex.Single

object MainRepository {

    fun getPhotoList(page: Int, limit: Int): Single<List<Photo>>{
        return RetrofitBuilder.apiService.getPhotoList(page, limit)
    }
}