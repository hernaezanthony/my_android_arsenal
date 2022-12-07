package com.ahernaez.retrofitsample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahernaez.retrofitsample.model.Photo
import com.ahernaez.retrofitsample.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(){

    private val compositeDisposable =  CompositeDisposable()
    private val _state = MutableLiveData<MainState>()

    fun getState(): LiveData<MainState> = _state

    fun getPhotoList(page: Int, limit: Int){

        val photoList = MutableLiveData<List<Photo>>()

        _state.value = MainState.Loading

        compositeDisposable.add(
            MainRepository.getPhotoList(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list->
                    _state.value = MainState.PhotoList(list)
                    photoList.postValue(list)
                },{
                    _state.value = MainState.Error(it.message.toString())
                })
        )

    }
}