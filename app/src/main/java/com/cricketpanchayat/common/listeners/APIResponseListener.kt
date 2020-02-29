package com.cricketpanchayat.common.listeners

import com.cricketpanchayat.network.APIResponse
import com.cricketpanchayat.network.ErrorInfo

/**
 * Created by o8428 on 9/18/2017.
 */

interface APIResponseListener {

    fun onSuccess(apiResponse: APIResponse?)
    fun onError(errorInfo: ErrorInfo?)

}
