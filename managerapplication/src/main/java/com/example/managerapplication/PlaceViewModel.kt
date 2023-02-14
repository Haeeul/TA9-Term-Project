package com.example.managerapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerapplication.data.repository.PlaceRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.URLDecoder

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    val API_SERVICE_KEY = "JppmyKXYZ0shc7Ln4qsZ4wEGK5fxMmjUDHYym%2FCVj5ahFlGGAJkNnsrKOtXoRLfKe4v5kokwifID9KxyLaRIPg%3D%3D"
    val decodingKey = "JppmyKXYZ0shc7Ln4qsZ4wEGK5fxMmjUDHYym/CVj5ahFlGGAJkNnsrKOtXoRLfKe4v5kokwifID9KxyLaRIPg=="


    fun getChargingStationList(){
        viewModelScope.launch {
            try{
                Log.d("명", repository.getChargingStation(URLDecoder.decode(API_SERVICE_KEY, "UTF-8")).toString()+" / "+URLDecoder.decode(API_SERVICE_KEY, "UTF-8"))
            }catch (e : HttpException){
                Log.d("명1", e.message())
            }

        }
    }
}