package com.bbo.mobiledevexam.util.extension

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.model.CustomFont
import java.io.IOException

fun View.makeVisible(){
    visibility = View.VISIBLE
}

fun View.makeGone(){
    visibility = View.GONE
}

fun Context.getCustomFont(font: CustomFont) : Typeface {
    return Typeface.createFromAsset(assets, font.path)
}

fun Context.messageFormat(name: String?): SpannableString {
    val msg = "$name ${resources.getString(R.string.text_item_added_suffix)}"
    val typeface = getCustomFont(CustomFont.MontserratBold)
    return SpannableString(msg).apply {
        setSpan(StyleSpan(typeface.style), 0, name?.length ?: 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

internal fun Context.getLocalJson(path: String): String? {
    return getJsonDataFromAsset(
        applicationContext,
        path
    )
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

