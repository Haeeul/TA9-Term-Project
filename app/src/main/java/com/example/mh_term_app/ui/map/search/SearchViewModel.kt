package com.example.mh_term_app.ui.map.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val mapRepository = MapRepository()

    val searchTxt = MutableLiveData<String>()

    private val _placeInfo = MutableLiveData<ResponseCategoryPlace>()
    val placeInfo : LiveData<ResponseCategoryPlace>
        get() = _placeInfo

    fun getSearchInfo(){
        viewModelScope.launch {
            _placeInfo.value = mapRepository.getSearchInfo(searchTxt.value.toString())
        }
    }
}