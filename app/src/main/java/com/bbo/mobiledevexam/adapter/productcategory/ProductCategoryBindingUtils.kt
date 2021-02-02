package com.bbo.mobiledevexam.adapter.productcategory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.model.Category
import com.bbo.mobiledevexam.util.extension.makeGone
import com.bbo.mobiledevexam.util.extension.makeVisible

@BindingAdapter("setCategoryDisplayIfSelected")
fun View.setCategoryDisplayIfSelected(category: Category) {
    if(category.isSelected) {
        setBackgroundResource(R.drawable.outline_active)
    }
    else {
        setBackgroundResource(0)
    }
}

@BindingAdapter("setCategoryText")
fun TextView.setCategoryText(title: String?) {
    text = if(!title.equals("All")) title.plus("s") else title
}

@BindingAdapter("setCrossVisibility")
fun ImageView.setCrossVisibility(isSelected: Boolean) {
    if(isSelected) {
        makeVisible()
    }
    else {
        makeGone()
    }
}