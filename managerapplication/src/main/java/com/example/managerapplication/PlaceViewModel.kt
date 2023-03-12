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
import com.example.managerapplication.data.model.request.RequestPublicToilet
import com.example.managerapplication.data.model.response.ChargingStationListResponse
import com.example.managerapplication.data.model.response.MovementCenterListResponse
import com.example.managerapplication.data.model.response.PublicToiletListResponse
import com.example.managerapplication.data.repository.PlaceRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    val API_SERVICE_KEY = "NiBhI42sA2qFT6izOTs7XMx%2FDYGfa7LwpVRLdrIcX80aQ4QL%2BdzxcwcgIuiFST6xprT6XZjaPwgP98Q6BE%2FaFg%3D%3D"
    val decodingKey = "+SqFOApfQNZgbUN9l8vghoHYLr9ZziKmIf7MiAXH5ZVQQrFSqTKWheTuaDfksCAJCvIZMpWazUK9aCJzBFdQUA=="

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
                Log.d("getChargingStationList : ", _chargingList.value!!.size.toString())

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
                Log.d("getMovementList : ", _centerList.value!!.size.toString())
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
                Log.d("getPublicToiletList : ", _toiletList.value!!.size.toString())
            }catch (e : HttpException){
                Log.w("getPublicToiletList error : ", e.message())
            }

        }
    }

    fun postChargingStation(){
        var count = 1
        viewModelScope.launch {
            _chargingList.value?.forEach {
                repository.postCharging(
                    RequestChargingStation(
                        type = "충전소",
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
                        phoneUse = it.moblphonChrstnYn,
                        managementName = it.institutionNm,
                        phone = it.institutionPhoneNumber,
                        referenceDate = it.referenceDate
                    )
                )
                Log.d("충전소 : $count", it.toString())
                count++
            }
        }
    }

    fun postMoveCenter(){
        var count = 1
        viewModelScope.launch {
            _centerList.value?.forEach {
                repository.postCenter(
                    RequestMovementCenter(
                        type = "이동지원센터",
                        name = it.tfcwkerMvmnCnterNm,
                        address = it.rdnmadr,
                        oldAddress = it.lnmadr,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        carCount = it.carHoldCo,
                        carKind = it.carHoldKnd,
                        slopeCarCount = it.slopeVhcleCo,
                        liftCarCount = it.liftVhcleCo,
                        phone = it.rceptPhoneNumber,
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
                        managementPhone = it.phoneNumber,
                        referenceDate = it.referenceDate
                    )
                )
                Log.d("센터 : $count", it.toString())
                count++
            }
        }
    }

    fun postPublicToilet(){
        var count = 1
        viewModelScope.launch {
            _toiletList.value?.forEach {
                repository.postToilet(
                    RequestPublicToilet(
                        type = "화장실",
                        detailType = it.toiletType,
                        name = it.toiletNm,
                        address = it.rdnmadr,
                        oldAddress = it.lnmadr,
                        unisex = it.unisexToiletYn,
                        menBow = it.menToiletBowlNumber,
                        menUrine = it.menUrineNumber,
                        menHandicapBow = it.menHandicapToiletBowlNumber,
                        menHandicapUrine = it.menHandicapUrinalNumber,
                        menChildrenBow = it.menChildrenToiletBowlNumber,
                        menChildrenUrine = it.menChildrenUrinalNumber,
                        womenBow = it.ladiesToiletBowlNumber,
                        womenHandicapBow = it.ladiesHandicapToiletBowlNumber,
                        womenChildrenBow = it.ladiesChildrenToiletBowlNumber,
                        managementName = it.institutionNm,
                        phone = it.phoneNumber,
                        time = getToiletTime(it.openTime),
                        latitude = it.latitude,
                        longitude = it.longitude,
                        referenceDate = it.referenceDate
                    )
                )
                Log.d("화장실 : $count", it.toString())
                count++
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

    private fun getToiletTime(time : String) : Time {
        return if(time == "24시간"||time =="상시"||time =="연중무휴") Time("0","0","23","59")
        else Time(getHour(time), time.substring(3,5), getHour(time.substring(6,8)), time.substring(9))
    }
}