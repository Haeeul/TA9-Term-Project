package com.example.mh_term_app.ui.menu.report

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.*
import com.example.mh_term_app.data.model.request.RequestPlaceFacility
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.request.RequestUpdatePlaceAddress
import com.example.mh_term_app.data.model.request.RequestUpdateStoreInfo
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class ReportViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    // 위치, 유형, 상세 정보/상세위치, 상세 유형, 주의 대상, 주의사항, 추가 설명 입력
    val addressTxt = MutableLiveData<String>()
    val detailAddressTxt = MutableLiveData<String>()
    val locationTxt = MutableLiveData<String>()
    val typeTxt = MutableLiveData<String>()

    val storeNameTxt = MutableLiveData<String>()
    val storePhoneTxt = MutableLiveData<String>()
    var storeWeekTimeTxt = MutableLiveData<String>()
    val storeSaturdayTimeTxt = MutableLiveData<String>()
    val storeMondayTimeTxt = MutableLiveData<String>()

    private var storeTime = MutableLiveData(StoreTime(Time("-2","-2","-2","-2"),
        Time("-2","-2","-2","-2"),Time("-2","-2","-2","-2")))

    val detailTypeTxt = MutableLiveData<String>()
    val etcTypeTxt = MutableLiveData<String>()

    val targetList = MutableLiveData<MutableList<String>?>()
    val warningList = MutableLiveData<MutableList<String>?>()

    val plusInfoTxt = MutableLiveData<String>()

    // 다음 버튼 활성화
    private val _isValidNextBtn = MutableLiveData(false)
    val isValidNextBtn : LiveData<Boolean>
        get() = _isValidNextBtn

    // store - 완료 버튼 활성화
    private val _isValidCompleteBtn = MutableLiveData(false)
    val isValidStoreCompleteBtn : LiveData<Boolean>
        get() = _isValidCompleteBtn

    // 제보 결과
    private val _isValidPost = MutableLiveData<Boolean>()
    val isValidPost : LiveData<Boolean>
        get() = _isValidPost

    // update - 보내기 버튼 활성화
    private val _isValidSendBtn = MutableLiveData(false)
    val isValidSendBtn : LiveData<Boolean>
        get() = _isValidSendBtn

    private var originStoreInfo = UpdateStoreInfo()
    private var originFacilityInfo = UpdateFacilityInfo()

    fun setStoreInfo(storeDetailInfo : UpdateStoreInfo){
        originStoreInfo = storeDetailInfo

        storeNameTxt.value = storeDetailInfo.name
        storePhoneTxt.value = checkNoneData(storeDetailInfo.phone)

        setTimeValue("week", storeDetailInfo.time.weekTime)
        setTimeValue("saturday", storeDetailInfo.time.saturdayTime)
        setTimeValue("monday", storeDetailInfo.time.mondayTime)

        if(storeDetailInfo.detailType != "음식점" && storeDetailInfo.detailType != "카페" ) etcTypeTxt.value = storeDetailInfo.detailType
        detailTypeTxt.value = storeDetailInfo.detailType

        setTargetList(storeDetailInfo.targetList)
        setWarningList(storeDetailInfo.warningList)

        plusInfoTxt.value = storeDetailInfo.plusInfo
    }

    fun setFacilityInfo(facilityInfo : UpdateFacilityInfo){
        originFacilityInfo = facilityInfo

        locationTxt.value = facilityInfo.location
        detailTypeTxt.value = facilityInfo.detailType

        setTargetList(facilityInfo.targetList)
        setWarningList(facilityInfo.warningList)

        plusInfoTxt.value = facilityInfo.plusInfo
    }

    private fun checkNoneData(data : String) : String{
        return if(data == "none") ""
        else data
    }

    private fun setTargetList(list : MutableList<String>?){
        var tempList = mutableListOf<String>()

        list?.forEach {
            tempList.add(it)
        }

        targetList.value = tempList
    }

    private fun setWarningList(list : MutableList<String>?){
        var tempList = mutableListOf<String>()

        list?.forEach {
            tempList.add(it)
        }

        warningList.value = tempList
    }

    fun checkStoreInfoUpdateBtn(){
        _isValidSendBtn.value = (originStoreInfo.name != storeNameTxt.value || originStoreInfo.phone != storePhoneTxt.value ||
                originStoreInfo.time != storeTime.value || originStoreInfo.detailType != detailTypeTxt.value ||
                checkNewList(targetList.value,originStoreInfo.targetList) || checkNewList(warningList.value,originStoreInfo.warningList) ||
                originStoreInfo.plusInfo != plusInfoTxt.value)

    }

    fun checkFacilityInfoUpdateBtn(){
        _isValidSendBtn.value = (originFacilityInfo.location != locationTxt.value || originFacilityInfo.detailType != detailTypeTxt.value ||
                checkNewList(targetList.value,originFacilityInfo.targetList) || checkNewList(warningList.value,originFacilityInfo.warningList) ||
                originFacilityInfo.plusInfo != plusInfoTxt.value)
    }

    private fun checkNewList(new : MutableList<String>?, origin : MutableList<String>?) : Boolean {
        if(new?.size != origin?.size) return true

        new?.forEach {
            Log.d("forEach", it)
            Log.d("contains", origin?.contains(it).toString())
            if(origin?.contains(it) != true) return true
        }

        if(new != origin) return true

        return false
    }

    fun setTypeTxt(txt:String){
        typeTxt.value = txt
    }

    fun setDetailTypeTxt(txt: String){
        detailTypeTxt.value = txt
    }

    fun getAddress(lat: Double, lng: Double) {
        val geoCoder = Geocoder(MHApplication.getApplicationContext(), Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."
        try {
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                addressResult = currentLocationAddress
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        addressTxt.value = addressResult
    }

    fun setTimeValue(type : String, time: Time){
        when(type){
            "week" -> {
                storeTime.value?.weekTime = time
                storeWeekTimeTxt.value = storeTime.value?.weekTime?.let { checkTimeType(it) }
            }
            "saturday" -> {
                storeTime.value?.saturdayTime = time
                storeSaturdayTimeTxt.value = storeTime.value?.saturdayTime?.let { checkTimeType(it) }
            }
            "monday" -> {
                storeTime.value?.mondayTime = time
                storeMondayTimeTxt.value = storeTime.value?.mondayTime?.let { checkTimeType(it) }
            }
        }
    }

    private fun checkTimeType(time: Time) : String{
        var timeTxt = ""
        timeTxt = when (time.openHourTxt) {
            "-1" -> "휴무"
            "-2" -> ""
            else -> {
                setTimePickerValue(time.openHourTxt.toInt(),time.openMinuteTxt.toInt()) + " ~ " + setTimePickerValue(time.closeHourTxt.toInt(),time.closeMinuteTxt.toInt())
            }
        }

        return timeTxt
    }

    private fun setTimePickerValue(hour : Int, minute : Int) : String{
        var timeTxt = ""
        var h = hour
        val m = minute

        if (h > 12) {
            h -= 12
            timeTxt = "오후 " + h + "시 " + m + "분"
        }else{
            timeTxt = "오전 " + h + "시 " + m + "분"
        }

        return timeTxt
    }

    private fun checkPickTime(txt: String) : Int{
        return if(txt.isEmpty()) 0
        else txt.toString().toInt()
    }

    fun clickTargetBtn(checked : Boolean, target : String){
        var tempTargetList = mutableListOf<String>()

        if(targetList.value?.isNotEmpty() == true){
            tempTargetList = targetList.value!!
        }

        if(checked) tempTargetList.add(target)
        else tempTargetList.remove(target)

        targetList.value = tempTargetList
    }

    fun clickWarningBtn(checked : Boolean, warning : String){
        var tempWarningList = mutableListOf<String>()

        if(warningList.value?.isNotEmpty() == true){
            tempWarningList = warningList.value!!
        }

        if(checked) tempWarningList.add(warning)
        else tempWarningList.remove(warning)

        warningList.value = tempWarningList

        Log.d("주의 사항 클릭",originStoreInfo.warningList.toString())
        Log.d("주의 사항 클릭",warningList.value.toString())
    }

    fun checkValidNextBtn(){
        _isValidNextBtn.value = typeTxt.value?.isNotEmpty() == true
    }

    fun checkValidStoreCompleteBtn(){
        _isValidCompleteBtn.value = storeNameTxt.value?.isNotEmpty() == true && detailTypeTxt.value?.isNotEmpty() == true && plusInfoTxt.value?.isNotEmpty() == true
    }

    fun postReportStore(storeInfo : ReportPlaceAddress){
        viewModelScope.launch {
            val store = RequestPlaceStore(
                type = storeInfo.type,
                address = storeInfo.address,
                detailAddress = checkDataValue(storeInfo.detailAddress),
                latitude = storeInfo.latitude,
                longitude = storeInfo.longitude,
                name = storeNameTxt.value.toString(),
                phone = checkDataValue(storePhoneTxt.value.toString()),
                time = storeTime.value!!,
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            _isValidPost.value = mapRepository.postReportStore(store)
        }
    }

    fun checkValidFacilityCompleteBtn(){
        _isValidCompleteBtn.value = locationTxt.value?.isNotEmpty() == true && detailTypeTxt.value?.isNotEmpty() == true && plusInfoTxt.value?.isNotEmpty() == true
    }

    fun postReportFacility(facilityInfo : ReportPlaceAddress){
        viewModelScope.launch {
            val facility = RequestPlaceFacility(
                type = facilityInfo.type,
                address = facilityInfo.address,
                detailAddress = checkDataValue(facilityInfo.detailAddress),
                latitude = facilityInfo.latitude,
                longitude = facilityInfo.longitude,
                location = locationTxt.value.toString(),
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            _isValidPost.value = mapRepository.postReportFacility(facility)
        }
    }

    // 위치 정보 수정 제안 결과
    private val _isValidUpdateAddress = MutableLiveData<Boolean>()
    val isValidUpdateAddress : LiveData<Boolean>
        get() = _isValidUpdateAddress

    fun postUpdateAddress(id : String, placeAddress: ReportPlaceAddress, latitude : Double, longitude : Double){
        viewModelScope.launch {
            val place = RequestUpdatePlaceAddress(
                id,
                "위치",
                placeAddress.address,
                checkDataValue(placeAddress.detailAddress),
                placeAddress.latitude,
                placeAddress.longitude,
                addressTxt.value.toString(),
                checkDataValue(detailAddressTxt.value.toString()),
                latitude,longitude
            )

            _isValidUpdateAddress.value = mapRepository.postUpdateAddress(place)
        }
    }

    private fun checkDataValue(item : String):String{
        return if(item == "null" || item.isBlank()) "none"
        else item
    }

    // 매장 정보 수정 제안 결과
    private val _isValidUpdateStore = MutableLiveData<Boolean>()
    val isValidUpdateStore : LiveData<Boolean>
        get() = _isValidUpdateStore

    fun postUpdateStoreInfo(id : String){
        viewModelScope.launch {
            val store = RequestUpdateStoreInfo(
                id,
                "상세정보",
                name = storeNameTxt.value.toString(),
                phone = checkDataValue(storePhoneTxt.value.toString()),
                time = storeTime.value!!,
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            _isValidUpdateStore.value = mapRepository.postUpdateStoreInfo(store)
        }
    }
}