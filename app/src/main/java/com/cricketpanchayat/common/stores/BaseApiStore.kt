package com.cricketpanchayat.common.stores

import com.cricketpanchayat.common.listeners.APIResponseListener
import com.cricketpanchayat.network.APIResponse
import com.cricketpanchayat.network.SystemErrorHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseApiStore {

    protected fun <T> interactionResultHandler(response: Observable<T>, clazz: Class<out APIResponse>): Observable<T> {
        return response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(
            SystemErrorHandler(clazz)
        )
    }

    protected fun evaluateServiceResponse(apiResponse: APIResponse, listener: APIResponseListener) {
        if (apiResponse.result!!.isSuccess) {
            listener.onSuccess(apiResponse)
        } else {
            listener.onError(apiResponse.result!!.errorInfo)
        }
    }
}