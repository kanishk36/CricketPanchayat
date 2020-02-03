package com.cricketpanchayat.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cricketpanchayat.common.base.BaseViewModel
import com.cricketpanchayat.common.stores.HomeTabApiStore
import com.cricketpanchayat.models.categorymenu.Category
import com.cricketpanchayat.models.categorymenu.CategoryResponse
import com.cricketpanchayat.network.APIResponse

class SplashViewModel: BaseViewModel() {

    val categoryListData: MutableLiveData<ArrayList<Category>> = MutableLiveData()

    fun fetchCategoryList() {
        HomeTabApiStore.fetchCategoryList(this)
    }

    override fun onSuccess(apiResponse: APIResponse?) {
        super.onSuccess(apiResponse)
        categoryListData.value = (apiResponse as CategoryResponse).category_array
    }

}