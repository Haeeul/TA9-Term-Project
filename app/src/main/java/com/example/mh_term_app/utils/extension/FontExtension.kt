package com.example.mh_term_app.utils.extension

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.mh_term_app.R

fun TextView.setTextBold(isBold:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.gyeonggi_cheonnyeon_bold else R.font.gyeonggi_cheonnyeon_medium)
}