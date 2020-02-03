package com.cricketpanchayat.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cricketpanchayat.common.listeners.ProgressIndicator
import com.cricketpanchayat.network.APIResponse
import com.cricketpanchayat.network.ErrorInfo
import com.fab.corporatebanking.common.listener.APIResponseListener

abstract class BaseViewModel: ViewModel(), APIResponseListener, ProgressIndicator {

    private val showLoading: MutableLiveData<String> = MutableLiveData()
    private val dismissLoading: MutableLiveData<String> = MutableLiveData()

    private val successResponse: MutableLiveData<APIResponse> by lazy {
        MutableLiveData< APIResponse >()
    }

    private val errorResponse: MutableLiveData<ErrorInfo> by lazy {
        MutableLiveData< ErrorInfo >()
    }

    override fun onStartProgress() {
        showLoading.postValue("")
    }

    override fun onUpdateProgress(percentage: Int) {
    }

    override fun onEndProgress() {
        dismissLoading.postValue("")
    }

    fun errorResponse(): MutableLiveData<ErrorInfo>{
        return errorResponse
    }

    fun successResponse(): MutableLiveData<APIResponse>{
        return successResponse
    }

    fun showLoading(): MutableLiveData<String>{
        return showLoading
    }

    fun dismissLoading(): MutableLiveData<String>{
        return dismissLoading
    }


    override fun onSuccess(apiResponse: APIResponse?) {
        successResponse.value = apiResponse
    }

    override fun onError(errorInfo: ErrorInfo?) {
        errorResponse.value = errorInfo
    }
}