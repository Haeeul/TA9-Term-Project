package com.example.mh_term_app.ui.menu.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.model.request.RequestReportFacility
import com.example.mh_term_app.data.model.request.RequestReportStore
import com.example.mh_term_app.data.repository.MapRepository
import kotlinx.coroutines.launch

class ReportViewModel : ViewModel() {
    private val mapRepository = MapRepository()

    // 위치, 유형, 상세 정보/상세위치, 상세 유형, 주의 대상, 주의사항, 추가 설명 입력
    val addressTxt = MutableLiveData<String>()
    val locationTxt = MutableLiveData<String>()
    val typeTxt = MutableLiveData<String>()

    val storeNameTxt = MutableLiveData<String>()
    val storePhoneTxt = MutableLiveData<String>()
    val storeTimeTxt = MutableLiveData<String>()

    val detailTypeTxt = MutableLiveData<String>()

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
        _isValidNextBtn.value = addressTxt.value?.isNotEmpty() == true &&  typeTxt.value?.isNotEmpty() == true
    }

    fun checkValidStoreCompleteBtn(){
        _isValidCompleteBtn.value = storeNameTxt.value?.isNotEmpty() == true && detailTypeTxt.value?.isNotEmpty() == true && plusInfoTxt.value?.isNotEmpty() == true
    }

    fun postReportStore(type : String, address : String){
        viewModelScope.launch {
            val store = RequestReportStore(
                type = type,
                address = address,
                name = storeNameTxt.value.toString(),
                phone = if(storePhoneTxt.value.toString()== "null"){"none"} else {storePhoneTxt.value.toString()},
                time = if(storeTimeTxt.value.toString()== "null"){"none"} else {storeTimeTxt.value.toString()},
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            Log.d("명",store.toString() +" / "+ store.targetList?.isEmpty().toString() +" / "+ store.warningList?.isEmpty().toString())
            _isValidPost.value = mapRepository.postReportStore(store)
        }
    }

    fun checkValidFacilityCompleteBtn(){
        _isValidCompleteBtn.value = locationTxt.value?.isNotEmpty() == true && detailTypeTxt.value?.isNotEmpty() == true && plusInfoTxt.value?.isNotEmpty() == true
    }

    fun postReportFacility(type : String, address : String){
        viewModelScope.launch {
            val store = RequestReportFacility(
                type = type,
                address = address,
                location = locationTxt.value.toString(),
                detailType = detailTypeTxt.value.toString(),
                targetList = targetList.value,
                warningList = warningList.value,
                plusInfo = plusInfoTxt.value.toString()
            )

            Log.d("명",store.toString() +" / "+ store.targetList?.isEmpty().toString() +" / "+ store.warningList?.isEmpty().toString())
//            _isValidPost.value = mapRepository.postReportFacility(store)
        }
    }
}