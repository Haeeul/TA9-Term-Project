package com.example.mh_term_app.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.response.ResponsePlaceStore
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    private val _storeList = MutableLiveData<MutableList<ResponsePlaceStore>>()
    val storeList : LiveData<MutableList<ResponsePlaceStore>>
        get() = _storeList

    fun getStoreList(){
        viewModelScope.launch {
            _storeList.value = mapRepository.getStoreList("매장")
        }
    }
}