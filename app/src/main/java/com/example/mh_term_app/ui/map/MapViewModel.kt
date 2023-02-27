package com.example.mh_term_app.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.response.ResponseCategoryList
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    private val _categoryList = MutableLiveData<MutableList<ResponseCategoryList>>()
    val categoryList : LiveData<MutableList<ResponseCategoryList>>
        get() = _categoryList

    fun getCategoryList(type : String){
        viewModelScope.launch {
            _categoryList.value = mapRepository.getCategoryList(type)
        }
    }
}