package com.example.mh_term_app.data.model.response

import com.example.mh_term_app.data.model.request.RequestPlaceStore

data class ResponsePlace(
    val id : String = "",
    val data : RequestPlaceStore
)