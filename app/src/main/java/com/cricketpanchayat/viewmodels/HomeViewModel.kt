package com.cricketpanchayat.viewmodels

import androidx.lifecycle.MutableLiveData
import com.cricketpanchayat.common.base.BaseViewModel
import com.cricketpanchayat.common.stores.HomeTabApiStore
import com.cricketpanchayat.models.home.*
import com.cricketpanchayat.network.APIResponse
import com.cricketpanchayat.utils.AppCache
import com.cricketpanchayat.utils.AppConstants

class HomeViewModel : BaseViewModel() {

    val feedsData: MutableLiveData<ArrayList<Feed>> = MutableLiveData()
    var slugTag: String = ""

    fun fetchLatestFeeds(showProgress: Boolean) {
        HomeTabApiStore.fetchLatestFeeds(this, this, showProgress)
    }

    fun fetchMostViewedFeeds(showProgress: Boolean) {
        HomeTabApiStore.fetchMostViewedFeeds(this, this, showProgress)
    }

    fun fetchCategoryFeeds(slug: String) {
        slugTag = slug
        HomeTabApiStore.fetchCategoryFeeds(slug, this, this)
    }

    fun fetchSearchData(query: String) {
        HomeTabApiStore.fetchSearchFeeds(query, this, this)
    }

    override fun onSuccess(apiResponse: APIResponse?) {
        super.onSuccess(apiResponse)
        if(apiResponse is LatestFeedsResponse) {
            feedsData.value = apiResponse.first_array
            AppCache.INSTANCE.addToAppCache(AppConstants.LATEST_FEED_TAG, apiResponse.first_array)

        } else if(apiResponse is MostViewedResponse) {
            feedsData.value = apiResponse.first_array
            AppCache.INSTANCE.addToAppCache(AppConstants.MOST_VIEWED_TAG, apiResponse.first_array)

        } else if(apiResponse is CategoryDataResponse) {
            feedsData.value = apiResponse.category_array
            AppCache.INSTANCE.addToAppCache(slugTag, apiResponse.category_array)

        } else if(apiResponse is SearchResponse) {
            feedsData.value = apiResponse.category_array
        }
    }

}
