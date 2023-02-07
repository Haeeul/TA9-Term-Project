package com.example.mh_term_app.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel(){
//    protected val _fetchState = MutableLiveData<FetchState>()
//    val fetchState : LiveData<FetchState>
//        get() = _fetchState
//
//    protected val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
//        throwable.printStackTrace()
//
//        when(throwable){
//            is SocketException -> _fetchState.postValue(FetchState.BAD_INTERNET)
//            is HttpException -> _fetchState.postValue(FetchState.PARSE_ERROR)
//            is UnknownHostException -> _fetchState.postValue(FetchState.WRONG_CONNECTION)
//            else -> _fetchState.postValue(FetchState.FAIL)
//        }
//    }
}