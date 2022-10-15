package com.ahernaez.retrofitsample.viewmodel

import com.ahernaez.retrofitsample.model.Photo

sealed class MainState {

    object Loading: MainState()
    data class PhotoList(val data: List<Photo>): MainState()
    data class Error(val errorMsg: String): MainState()
}