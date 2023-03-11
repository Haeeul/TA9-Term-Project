package com.example.mh_term_app.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.request.RequestPlaceFacility
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.data.model.response.ResponseChargingStation
import com.example.mh_term_app.data.model.response.ResponseMoveCenter
import com.example.mh_term_app.data.model.response.ResponsePublicToilet
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    private val _categoryList = MutableLiveData<MutableList<ResponseCategoryPlace>>()
    val categoryList : LiveData<MutableList<ResponseCategoryPlace>>
        get() = _categoryList

    private val _placeRating = MutableLiveData<Float>()
    val placeRating : LiveData<Float>
        get() = _placeRating

    private val _storeInfo = MutableLiveData<RequestPlaceStore>()
    val storeInfo : LiveData<RequestPlaceStore>
        get() = _storeInfo

    private val _facilityInfo = MutableLiveData<RequestPlaceFacility>()
    val facilityInfo : LiveData<RequestPlaceFacility>
        get() = _facilityInfo

    private val _chargingInfo = MutableLiveData<ResponseChargingStation>()
    val chargingInfo : LiveData<ResponseChargingStation>
        get() = _chargingInfo

    private val _centerInfo = MutableLiveData<ResponseMoveCenter>()
    val centerInfo : LiveData<ResponseMoveCenter>
        get() = _centerInfo

    private val _toiletInfo = MutableLiveData<ResponsePublicToilet>()
    val toiletInfo : LiveData<ResponsePublicToilet>
        get() = _toiletInfo

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean>
        get() = _loading

    fun setLoading(visibility : Boolean){
        _loading.value = visibility
    }

    fun getCategoryList(type : String){
        viewModelScope.launch {
            _categoryList.value = mapRepository.getCategoryList(type)
        }
    }

    fun getPlaceRating(id : String){
        viewModelScope.launch {
            _placeRating.value = mapRepository.getPlaceRating(id)
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

    fun getChargingInfo(id : String){
        viewModelScope.launch {
            _chargingInfo.value = mapRepository.getChargingInfo(id)
        }
    }

    fun getCenterInfo(id : String){
        viewModelScope.launch {
            _centerInfo.value = mapRepository.getCenterInfo(id)
        }
    }

    fun getToiletInfo(id : String){
        viewModelScope.launch {
            _toiletInfo.value = mapRepository.getToiletInfo(id)
        }
    }
}