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

    val API_SERVICE_KEY = "NiBhI42sA2qFT6izOTs7XMx%2FDYGfa7LwpVRLdrIcX80aQ4QL%2BdzxcwcgIuiFST6xprT6XZjaPwgP98Q6BE%2FaFg%3D%3D"
    val decodingKey = "NiBhI42sA2qFT6izOTs7XMx/DYGfa7LwpVRLdrIcX80aQ4QL+dzxcwcgIuiFST6xprT6XZjaPwgP98Q6BE/aFg=="


    fun getChargingStationList(){
        viewModelScope.launch {
            try{
                Log.d("명", repository.getChargingStation(decodingKey).toString())

//                Log.d("명", repository.getChargingStation(URLDecoder.decode(API_SERVICE_KEY, "UTF-8")).toString()+" / "+URLDecoder.decode(API_SERVICE_KEY, "UTF-8"))
            }catch (e : HttpException){
                Log.d("명1", e.message())
            }

        }
    }
}