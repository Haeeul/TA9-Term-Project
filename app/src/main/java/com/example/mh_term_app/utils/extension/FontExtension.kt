package com.example.mh_term_app.utils.extension

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.mh_term_app.R

fun TextView.setTextBold(isBold:Boolean){
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.gyeonggi_cheonnyeon_bold else R.font.gyeonggi_cheonnyeon_medium)
}

fun TextView.changeKeywordColor(str1: String, str2 : String, firstStart : Int, firstEnd : Int, secondStart : Int, secondEnd : Int) {
    val ssb1 = SpannableStringBuilder(str1)
    ssb1.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.green)),
        firstStart,firstEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    val ssb2 = SpannableStringBuilder(str2)
    ssb2.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.green)),
        secondStart,secondEnd,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(TextUtils.concat(ssb1,ssb2), TextView.BufferType.SPANNABLE)
}
