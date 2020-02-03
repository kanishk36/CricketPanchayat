package com.cricketpanchayat.models.categorymenu

import com.cricketpanchayat.network.APIResponse

class CategoryResponse: APIResponse() {

    lateinit var category_array: ArrayList<Category>
}