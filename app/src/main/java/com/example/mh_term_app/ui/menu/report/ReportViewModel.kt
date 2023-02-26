package com.example.mh_term_app.ui.menu.report

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.StoreTime
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.data.model.request.RequestReportFacility
import com.example.mh_term_app.data.model.request.RequestReportStore
import com.example.mh_term_app.data.repository.MapRepository
import com.google.common.primitives.UnsignedBytes.toInt
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

    private val targetList = MutableLiveData<MutableList<String>>()
    private val warningList = MutableLiveData<MutableList<String>>()

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
        timeTxt = if(time.openHourTxt == "-1" ) "휴무"
        else {
            setTimePickerValue(time.openHourTxt.toInt(),time.openMinuteTxt.toInt()) + " ~ " + setTimePickerValue(time.closeHourTxt.toInt(),time.closeMinuteTxt.toInt())
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
    }

    fun checkValidNextBtn(){
        _isValidNextBtn.value = typeTxt.value?.isNotEmpty() == true
    }

    fun checkValidStoreCompleteBtn(){
        _isValidCompleteBtn.value = storeNameTxt.value?.isNotEmpty() == true && detailTypeTxt.value?.isNotEmpty() == true && plusInfoTxt.value?.isNotEmpty() == true
    }

    fun postReportStore(type : String, address : String, latitude : String, longitude : String){
        viewModelScope.launch {
            val store = RequestReportStore(
                type = type,
                address = address,
                latitude = latitude,
                longitude = longitude,
                name = storeNameTxt.value.toString(),
                phone = if(storePhoneTxt.value.toString()== "null"){"none"} else {storePhoneTxt.value.toString()},
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

    fun postReportFacility(type : String, address : String, latitude : String, longitude : String){
        viewModelScope.launch {
            val facility = RequestReportFacility(
                type = type,
                address = address,
                latitude = latitude,
                longitude = longitude,
                location = locationTxt.value.toString(),
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            _isValidPost.value = mapRepository.postReportFacility(facility)
        }
    }
}