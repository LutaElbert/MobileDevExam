package com.bbo.mobiledevexam

import android.app.Application
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.network.response.JsonResponse

open class MobileDevExamApplication : Application() {

    var repository: ProductRepository? = null

    open val productResponse by lazy {
        JsonResponse(this, repository = repository)
    }

}