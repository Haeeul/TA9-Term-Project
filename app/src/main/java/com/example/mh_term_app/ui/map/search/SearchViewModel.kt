package com.example.mh_term_app.ui.map.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.local.RecentSearchDatabase
import com.example.mh_term_app.data.local.entity.RecentSearch
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.data.repository.MapRepository
import com.example.mh_term_app.data.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val db = RecentSearchDatabase.getInstance(MHApplication.getApplicationContext()).searchPlaceDao()
    private val searchRepo = SearchRepository(db)
    var recentSearchList = searchRepo.places

    private val mapRepository = MapRepository()

    val searchTxt = MutableLiveData<String>()

    private val _placeInfo = MutableLiveData<ResponseCategoryPlace>()
    val placeInfo : LiveData<ResponseCategoryPlace>
        get() = _placeInfo

    private val _isValidRecentList = MutableLiveData(false)
    val isValidRecentList : LiveData<Boolean>
        get() = _isValidRecentList

    fun getSearchInfo(){
        viewModelScope.launch {
            _placeInfo.value = mapRepository.getSearchInfo(searchTxt.value.toString())
        }
    }

    suspend fun deleteAllRecentSearch(){
        searchRepo.deleteAll()
    }

    suspend fun deleteDataRecentSearch(placeId : String){
        searchRepo.delete(placeId)
    }

    fun setValidRecentList(isValid : Boolean){
        _isValidRecentList.value = isValid
    }
}