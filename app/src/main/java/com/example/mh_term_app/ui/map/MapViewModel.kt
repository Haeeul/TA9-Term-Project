package com.example.mh_term_app.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.request.RequestReportFacility
import com.example.mh_term_app.data.model.response.ResponseCategoryList
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    private val _categoryList = MutableLiveData<MutableList<ResponseCategoryList>>()
    val categoryList : LiveData<MutableList<ResponseCategoryList>>
        get() = _categoryList

    private val _storeInfo = MutableLiveData<RequestPlaceStore>()
    val storeInfo : LiveData<RequestPlaceStore>
        get() = _storeInfo

    private val _facilityInfo = MutableLiveData<RequestReportFacility>()
    val facilityInfo : LiveData<RequestReportFacility>
        get() = _facilityInfo

    fun getCategoryList(type : String){
        viewModelScope.launch {
            _categoryList.value = mapRepository.getCategoryList(type)
        }
    }

    fun getStoreInfo(id : String){
        viewModelScope.launch {
            _storeInfo.value = mapRepository.getStoreInfo(id)
        }
    }

    fun getFacilityInfo(id : String){
        viewModelScope.launch {
            _facilityInfo.value = mapRepository.getFacilityInfo(id)
        }
    }
}