package com.example.mh_term_app.utils.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel(){

    val openHourTxt = MutableLiveData<String>("")
    val openMinuteTxt = MutableLiveData<String>("")
    val closeHourTxt = MutableLiveData<String>("")
    val closeMinuteTxt = MutableLiveData<String>("")

    val openTimeTxt = MutableLiveData<String>()
    val closeTimeTxt = MutableLiveData<String>()

    private val _isValidOpenTime = MutableLiveData(true)
    val isValidOpenTime : LiveData<Boolean>
        get() = _isValidOpenTime

    private val _isValidCloseTime = MutableLiveData(true)
    val isValidCloseTime : LiveData<Boolean>
        get() = _isValidCloseTime

    private val _isValidCompleteBtn = MutableLiveData(false)
    val isValidCompleteBtn : LiveData<Boolean>
        get() = _isValidCompleteBtn

    private fun setValidTime(isValid : Boolean, type : String){
        when(type){
            "open" -> _isValidOpenTime.value = isValid
            "close" -> _isValidCloseTime.value = isValid
        }
    }

    private fun setTimeTxt(txt : String, type: String){
        when(type){
            "open" -> openTimeTxt.value = txt
            "close" -> closeTimeTxt.value = txt
        }
    }

    fun setStoreTime(type: String){
        when(type){
            "open" -> {
                setValidTime(true,"open")
                setValidTime(true,"close")
                setTimePickerValue("open")
                setTimePickerValue("close")
            }
            "close" -> {
                setValidTime(false,"open")
                setValidTime(false,"close")
                setTimeTxt("휴무","open")
                setTimeTxt("휴무","close")
            }
            "allTime" -> {
                setValidTime(false,"open")
                setValidTime(false,"close")
                setTimeTxt("오전 0시 0분","open")
                setTimeTxt("오후 11시 59분","close")
            }
        }
    }

    fun setAutoTime(openHour : String, openMinute : String, closeHour : String, closeMinute : String){
        openHourTxt.value = openHour
        openMinuteTxt.value = openMinute
        closeHourTxt.value = closeHour
        closeMinuteTxt.value = closeMinute
    }

    fun setAllTime(isChecked: Boolean){
        if(isChecked) setStoreTime("allTime")
        else setStoreTime("open")
    }

    fun setTimePickerValue(type : String){
        var hour  = 0
        var minute = 0
        when(type){
            "open" -> {
                hour = checkPickTime(openHourTxt.value.toString())
                minute = checkPickTime(openMinuteTxt.value.toString())
            }
            "close" -> {
                hour = checkPickTime(closeHourTxt.value.toString())
                minute = checkPickTime(closeMinuteTxt.value.toString())
            }
        }
        if (hour > 12) {
            hour -= 12
            setTimeTxt("오후 " + hour + "시 " + minute + "분",type)
        } else if(hour == -1) {
            setTimeTxt("휴무",type)
        } else if(hour == -2){
            setTimeTxt("",type)
        }else{
            setTimeTxt("오전 " + hour + "시 " + minute + "분",type)
        }
    }

    private fun checkPickTime(txt: String) : Int{
        return if(txt.isEmpty()) -2
        else txt.toInt()
    }

    fun checkCompleteBtn(){
        _isValidCompleteBtn.value = openTimeTxt.value?.isNotEmpty() == true && closeTimeTxt.value?.isNotEmpty() == true
    }
}