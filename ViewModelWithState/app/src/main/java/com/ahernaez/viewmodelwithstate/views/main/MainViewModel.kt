package com.ahernaez.viewmodelwithstate.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<MainState>()

    fun getState(): LiveData<MainState> = _state

    init {
        getRandomWord()
    }

    fun getRandomWord(){

        _state.value = MainState.Loading

        compositeDisposable.add(

            MainRepository.getRandomWord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _state.value = MainState.RandomWord(it)
                },{
                    _state.value = MainState.Error(it.message!!)
                })
        )
    }

}