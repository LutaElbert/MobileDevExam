package com.bbo.mobiledevexam.presentation.screens.product

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.db.OrderDetailsTable
import com.bbo.mobiledevexam.db.OrderTable
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.model.Category
import com.bbo.mobiledevexam.model.ProductItemList
import com.bbo.mobiledevexam.model.ProductList
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProductViewModel(var application: Application, private val repository: ProductRepository) : ViewModel(), Observable {

    val cart = repository.getAllCart()

    var callback: Callback? = null

    private val compositeDisposable = CompositeDisposable()

    private var _productListResponse = MutableLiveData<ProductList>()
    val productResponse: LiveData<ProductList>?
        get() = _productListResponse

    private var _productItemList = MutableLiveData<MutableList<ProductItemList>>()
    val productItemList: LiveData<MutableList<ProductItemList>>
        get() = _productItemList

    private var _categories = MutableLiveData<MutableList<Category>>()
    val categories: LiveData<MutableList<Category>>
        get() = _categories

    private var result: MutableList<ProductItemList>? = mutableListOf()

    companion object {
        const val TAG = "ProductViewModel"
    }

    init {
        val productResponse = (application as MobileDevExamApplication).productResponse
        _productListResponse.value = productResponse.getProductList()
        _productItemList.value = productResponse.getProductList().products?.toMutableList()?.getProductItemList()
        _categories.value = productResponse.getProductList().products?.toMutableList()?.getCategories()
    }

    fun displayProductList(category: Category) {
        val products = productResponse?.value?.products?.toMutableList()?.getProductItemList()

        if(category.name == "All") {
            _categories.value?.let {
                it.toggleHeader(category)
            }

            _productItemList.value = products
            result = null
            return
        } else {

            _categories.value?.let {

                it.toggleItem(category)

                val noSelection = it.all { !it.isSelected }
                if (noSelection) {
                    it.first().isSelected = true
                    _productItemList.value = products
                    result = null
                    return
                }
            }

            val filterResult = mutableListOf<ProductItemList>()
            products?.forEach {
                if(it.category == category.name){
                    filterResult.add(it)
                }
            }

            result = finalizingResult(category, filterResult)

            _productItemList.value = result
        }
    }

    private fun finalizingResult(category: Category, list: MutableList<ProductItemList>): MutableList<ProductItemList>? {
        return result?.let {
            if (category.isSelected)
                it.plus(list).toMutableList()
            else
                it.minus(list).toMutableList()
        } ?: list
    }

    fun insert(id: String, onSuccess: ((product: ProductItemList) -> Unit)? = null) {
        val product = getProductById(id) ?: return
        getOrderTableRowCount {rowCount ->
            if (rowCount < 1)
                insertToCart(OrderDetailsTable(orderId = OrderTable.START_ID,productId = id)) {
                    onSuccess?.invoke(product)
                }
            else
                getMaxOrderTableId{maxId ->
                    insertToCart(OrderDetailsTable(orderId = maxId, productId = id)) {
                        onSuccess?.invoke(product)
                    }
                }
        }
    }

    private fun insertToCart(order: OrderDetailsTable, onSuccess: () -> Unit) {
        val disposable = repository.insertCart(order)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ onSuccess.invoke() }

        compositeDisposable.add(disposable)
    }

    private fun getOrderTableRowCount(onSuccess : (id : Long) -> (Unit)) {
        repository.getOrderTableRowCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }

            })
    }

    private fun getMaxOrderTableId(onSuccess: (id: Long) -> Unit) {
        repository.getMaxOrderTableId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long>{
                override fun onSuccess(t: Long) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    private fun getProductById(id: String?) : ProductItemList? = _productItemList.value?.find { it.id == id }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    interface Callback {
        fun onError(message: String?)
    }

}