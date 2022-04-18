package com.ahernaez.retrofitsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahernaez.retrofitsample.model.Photo
import com.ahernaez.retrofitsample.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(){

    private val compositeDisposable =  CompositeDisposable()

    fun getPhotoList(page: Int, limit: Int): MutableLiveData<List<Photo>>{

        val photoList = MutableLiveData<List<Photo>>()

        compositeDisposable.add(
            MainRepository.getPhotoList(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list->
                    photoList.postValue(list)
                },{
                    photoList.postValue(null)
                })
        )

        return photoList
    }
}