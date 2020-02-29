package com.cricketpanchayat.common.stores

import com.cricketpanchayat.common.listeners.APIResponseListener
import com.cricketpanchayat.common.listeners.ProgressIndicator
import com.cricketpanchayat.models.categorymenu.CategoryResponse
import com.cricketpanchayat.models.home.CategoryDataResponse
import com.cricketpanchayat.models.home.LatestFeedsResponse
import com.cricketpanchayat.models.home.MostViewedResponse
import com.cricketpanchayat.models.home.SearchResponse
import com.cricketpanchayat.network.ServiceInvoker
import com.cricketpanchayat.network.SystemErrorHandler
import com.cricketpanchayat.utils.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object HomeTabApiStore: BaseApiStore() {

    fun fetchLatestFeeds(listener: APIResponseListener, progress: ProgressIndicator, showProgress: Boolean) {

        if(showProgress) {
            progress.onStartProgress()
        }

        val observable = ServiceInvoker.Instance.invoke<LatestFeedsResponse>(
            AppConstants.ServiceURLs.HOME_LATEST_URL, LatestFeedsResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, LatestFeedsResponse::class.java)
            .subscribe { response ->
                if(response.result!!.status == null) {
                    response.setSuccess()
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun fetchCategoryList(listener: APIResponseListener) {
        val observable = ServiceInvoker.Instance.invoke<CategoryResponse>(
            AppConstants.ServiceURLs.CATEGORY_URL, CategoryResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(
            SystemErrorHandler(CategoryResponse::class.java)
        ).subscribe { response ->
            if(response.result!!.status == null) {
                response.setSuccess()
            }
            evaluateServiceResponse(response, listener)
        }
    }

    fun fetchMostViewedFeeds(listener: APIResponseListener, progress: ProgressIndicator, showProgress: Boolean) {

        if(showProgress) {
            progress.onStartProgress()
        }

        val observable = ServiceInvoker.Instance.invoke<MostViewedResponse>(
            AppConstants.ServiceURLs.MOST_VIEWED_URL, MostViewedResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, MostViewedResponse::class.java)
            .subscribe { response ->
                if(response.result!!.status == null) {
                    response.setSuccess()
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun fetchCategoryFeeds(slug: String, listener: APIResponseListener, progress: ProgressIndicator) {

        progress.onStartProgress()

        val url = String.format(AppConstants.ServiceURLs.CATEGORY_DATA_URL, slug)

        val observable = ServiceInvoker.Instance.invoke<CategoryDataResponse>(
            url, CategoryDataResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, CategoryDataResponse::class.java)
            .subscribe { response ->
                if(response.result!!.status == null) {
                    response.setSuccess()
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }

    fun fetchSearchFeeds(query: String, listener: APIResponseListener, progress: ProgressIndicator) {
        progress.onStartProgress()

        val url = String.format(AppConstants.ServiceURLs.SEARCH_DATA_URL, query)

        val observable = ServiceInvoker.Instance.invoke<SearchResponse>(
            url, SearchResponse::class.java, AppConstants.HttpMethods.HTTP_GET
        )

        interactionResultHandler(observable, SearchResponse::class.java)
            .subscribe { response ->
                if(response.result!!.status == null) {
                    response.setSuccess()
                }

                evaluateServiceResponse(response, listener)
                progress.onEndProgress()
            }
    }
}