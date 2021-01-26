package com.bbo.mobiledevexam.util.extension

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bbo.mobiledevexam.enum.CustomFont
import java.io.IOException

fun Context.getCustomFont(font: CustomFont) : Typeface {
    return Typeface.createFromAsset(assets, font.path)
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
        //ioException.printStackTrace()
        Log.e("qweqwe", "qweqw ${ioException.message}")
        return null
    }
    return jsonString
}

