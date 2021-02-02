package com.bbo.mobiledevexam

import android.app.Application
import com.bbo.mobiledevexam.network.response.JsonResponse

open class MobileDevExamApplication : Application() {

    open val productResponse by lazy {
        JsonResponse(this)
    }

}