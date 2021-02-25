package com.bbo.mobiledevexam.adapter.productlist

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bbo.mobiledevexam.model.CustomFont
import kotlin.math.sign

@BindingAdapter("setCustomFont")
fun TextView.setCustomFont(font: CustomFont) {
    typeface = Typeface.createFromAsset(context.assets, font.path)
}

@BindingAdapter("setHexColor")
fun View.setHexColor(hexColor: String?) {
    hexColor?.let {
        val gradient = background.current as GradientDrawable
        gradient.setColor(Color.parseColor(hexColor))
    }
}

@BindingAdapter("setFormattedPriceText")
fun TextView.setFormattedPriceText(price: Double?) {
    price?.let {
        text = "$".plus("${price.toInt()}")
    }
}

@BindingAdapter("setQuantityText")
fun TextView.setQuantityText(quantity: Int?) {
    quantity?.let {
        text = quantity.toString()
    }
}

@BindingAdapter("setImageResourceByName")
fun ImageView.setImageResourceByName(filename: String?) {
    filename?.let {
        val resourceId = context.resources.getIdentifier(filename, "drawable", context.packageName)
        setImageResource(resourceId)
    }
}

@BindingAdapter("app:setBackgroundTint")
fun View.setBackgroundTint(hexColor: String?) {
    DrawableCompat.setTint(background, Color.parseColor(hexColor))
}