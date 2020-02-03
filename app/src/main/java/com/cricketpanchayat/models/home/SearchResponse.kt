package com.cricketpanchayat.models.home

import com.cricketpanchayat.network.APIResponse

class SearchResponse: APIResponse() {

    lateinit var category_array: ArrayList<Feed>
}