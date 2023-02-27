package com.example.mh_term_app.data.model

import com.naver.maps.map.overlay.Marker

data class MarkerList<T>(
    val marker : Marker,
    val data : T
)