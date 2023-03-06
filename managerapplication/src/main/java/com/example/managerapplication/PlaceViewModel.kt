package com.example.managerapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerapplication.data.model.CenterTime
import com.example.managerapplication.data.model.ChargingTime
import com.example.managerapplication.data.model.Time
import com.example.managerapplication.data.model.request.RequestChargingStation
import com.example.managerapplication.data.model.request.RequestMovementCenter
import com.example.managerapplication.data.model.response.ChargingStationListResponse
import com.example.managerapplication.data.model.response.MovementCenterListResponse
import com.example.managerapplication.data.model.response.PublicToiletListResponse
import com.example.managerapplication.data.repository.PlaceRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.URLDecoder

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    val API_SERVICE_KEY = "NiBhI42sA2qFT6izOTs7XMx%2FDYGfa7LwpVRLdrIcX80aQ4QL%2BdzxcwcgIuiFST6xprT6XZjaPwgP98Q6BE%2FaFg%3D%3D"
    val decodingKey = "NiBhI42sA2qFT6izOTs7XMx/DYGfa7LwpVRLdrIcX80aQ4QL+dzxcwcgIuiFST6xprT6XZjaPwgP98Q6BE/aFg=="

    private val _chargingList = MutableLiveData<MutableList<ChargingStationListResponse>>()
    val chargingList : LiveData<MutableList<ChargingStationListResponse>>
        get() = _chargingList

    private val _centerList = MutableLiveData<MutableList<MovementCenterListResponse>>()
    val centerList : LiveData<MutableList<MovementCenterListResponse>>
        get() = _centerList

    private val _toiletList = MutableLiveData<MutableList<PublicToiletListResponse>>()
    val toiletList : LiveData<MutableList<PublicToiletListResponse>>
        get() = _toiletList


    fun getChargingStationList(){
        viewModelScope.launch {
            try{
                _chargingList.value = repository.getChargingStation(decodingKey).response.body.items
                Log.d("getChargingStationList : ", _chargingList.value.toString())

            }catch (e : HttpException){
                Log.w("getChargingStationList error : ", e.message())
            }

        }
    }

    fun getMovementList(){
        viewModelScope.launch {
            try{
                _centerList.value = repository.getMovementCenter(decodingKey).response.body.items
                Log.d("getMovementList : ", _centerList.value.toString())

            }catch (e : HttpException){
                Log.w("getMovementList error : ", e.message())
            }

        }
    }

    fun getPublicToiletList(){
        viewModelScope.launch {
            try{
                _toiletList.value = repository.getPublicToiletList(decodingKey).response.body.items
                Log.d("getPublicToiletList : ", _toiletList.value.toString())

            }catch (e : HttpException){
                Log.w("getPublicToiletList error : ", e.message())
            }

        }
    }

    fun postChargingStation(){
        viewModelScope.launch {
            _chargingList.value?.forEach {
                repository.postCharging(
                    RequestChargingStation(
                        type = "charging",
                        name = it.fcltyNm,
                        address = it.rdnmadr,
                        oldAddress = it.lnmadr,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        description = it.instlLcDesc,
                        time = ChargingTime(
                            changeTimeValue(it.weekdayOperOpenHhmm,it.weekdayOperColseHhmm,"weekday"),
                            changeTimeValue(it.satOperOperOpenHhmm, it.satOperCloseHhmm, "holiday"),
                            changeTimeValue(it.holidayOperOpenHhmm, it.holidayCloseOpenHhmm, "holiday")
                        ),
                        sameUse = it.smtmUseCo,
                        airUse = it.airInjectorYn,
                        phoneUser = it.moblphonChrstnYn,
                        managementName = it.institutionNm,
                        phone = it.institutionPhoneNumber,
                        referenceDate = it.referenceDate,
                        managementCode = it.insttCode
                    )
                )
            }
        }
    }

    fun postMoveCenter(){
        viewModelScope.launch {
            _centerList.value?.forEach {
                repository.postCenter(
                    RequestMovementCenter(
                        type = "center",
                        name = it.tfcwkerMvmnCnterNm,
                        address = it.rdnmadr,
                        oldAddress = it.lnmadr,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        carCount = it.carHoldCo,
                        carKind = it.carHoldKnd,
                        slopeCarCount = it.slopeVhcleCo,
                        liftCarCount = it.liftVhcleCo,
                        reservationPhone = it.rceptPhoneNumber,
                        homepage = it.rceptItnadr,
                        appName = it.appSvcNm,
                        reservationTime = CenterTime(
                            changeTimeValue(it.weekdayRceptOpenHhmm,it.weekdayRceptColseHhmm,"weekday"),
                            changeTimeValue(it.wkendRceptOpenHhmm, it.wkendRceptCloseHhmm, "holiday")
                        ),
                        carRunTime = CenterTime(
                            changeTimeValue(it.weekdayOperOpenHhmm, it.weekdayOperColseHhmm, "weekday"),
                            changeTimeValue(it.wkendOperOpenHhmm, it.wkendOperCloseHhmm, "holiday")
                        ),
                        aheadTime = it.beffatResvePd,
                        limit = it.useLmtt,
                        insideArea = it.insideOpratArea,
                        outsideArea = it.outsideOpratArea,
                        useTarget = it.useTrget,
                        useCharge = it.useCharge,
                        managementName = it.institutionNm,
                        phone = it.phoneNumber,
                        referenceDate = it.referenceDate,
                        managementCode = it.insttCode
                    )
                )
            }
        }
    }

    private fun changeTimeValue(open : String, close : String, type : String) : Time {
        if(open.substring(0,2)=="00" && open.substring(3)=="00" && close.substring(0,2)=="00" && close.substring(3)=="00"){
            return if(type == "holiday") Time("-1","-1","-1","-1")
            else Time("-2","-2","-2","-2")
        }

        return Time(getHour(open), open.substring(3), getHour(close), close.substring(3))
    }

    private fun getHour(time : String) : String{
        return if(time[0].toString()=="0") time.substring(1,2)
        else time.substring(0,2)
    }
}