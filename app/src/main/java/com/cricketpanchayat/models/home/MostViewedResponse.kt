package com.cricketpanchayat.models.home

import com.cricketpanchayat.network.APIResponse

class MostViewedResponse: APIResponse() {

    lateinit var first_array: ArrayList<Feed>
}
