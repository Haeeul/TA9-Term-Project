package com.example.mh_term_app.ui.map.search

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.data.repository.MapRepository
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {
    val mapRepository = MapRepository()

    val searchTxt = MutableLiveData<String>()

    private val _placeInfo = MutableLiveData<ResponseCategoryPlace>()
    val placeInfo : LiveData<ResponseCategoryPlace>
        get() = _placeInfo

    private val _coordInfo = MutableLiveData<LatLng>()
    val coordInfo : LiveData<LatLng>
        get() = _coordInfo

    val addressWord = listOf("시","군","구","면","목","리","대로","로","길","번길")

    fun getSearchName(){
        viewModelScope.launch {
            _placeInfo.value = mapRepository.getSearchInfo(searchTxt.value.toString())
        }
    }

    fun getSearchAddress(){
        val geocoder = Geocoder(MHApplication.getApplicationContext())
        var addresses: List<Address?>? = null

        try {
            addresses = geocoder.getFromLocationName(searchTxt.toString(), 3)

            if (addresses != null && addresses.isNotEmpty()) {

                Log.d("명",addresses[0]!!.latitude.toString()+" / "+addresses[0]!!.longitude.toString())

                _coordInfo.value = LatLng(addresses[0]!!.latitude, addresses[0]!!.longitude)
            }
        } catch (e: Exception) {
        }
    }
}