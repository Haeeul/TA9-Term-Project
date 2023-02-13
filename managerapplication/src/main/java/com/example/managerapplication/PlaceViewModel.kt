package com.example.managerapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerapplication.data.repository.PlaceRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    val key = "JppmyKXYZ0shc7Ln4qsZ4wEGK5fxMmjUDHYym%2FCVj5ahFlGGAJkNnsrKOtXoRLfKe4v5kokwifID9KxyLaRIPg%3D%3D"
    val decodingKey = "JppmyKXYZ0shc7Ln4qsZ4wEGK5fxMmjUDHYym/CVj5ahFlGGAJkNnsrKOtXoRLfKe4v5kokwifID9KxyLaRIPg=="

    fun getChargingStationList(){
        viewModelScope.launch {
            try{
                Log.d("명", repository.getChargingStation(key).toString())
            }catch (e : HttpException){
                Log.d("명1", e.message())
            }

        }
    }
}