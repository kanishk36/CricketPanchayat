package com.cricketpanchayat.utils

class AppConstants {

    companion object {

        const val ERROR = "error"
        const val SUCCESS = "success"

        //error codes
        const val SYS_HTTP_ERROR = "SYS_HTTP_ERROR"
        const val SYS_IO_ERROR = "SYS_IO_ERROR"
        const val API_TIMEOUT_ERROR = "API_TIMEOUT_ERROR"
        const val SYS_DEFAULT_ERROR = "SYS_DEFAULT_ERROR"
        const val INTERNET_ERROR = "INTERNET_ERROR"

        // cache constants
        const val LATEST_FEED_TAG = "latest_feed"
        const val MOST_VIEWED_TAG = "most_viewed"
        const val CATEGORY_LIST_TAG = "category_list"

    }

    interface ServiceURLs {
        companion object {

            val BASE_URL = "https://www.cricketpanchayat.com/api/"
            val HOME_LATEST_URL = "latest.php"
            val CATEGORY_URL = "category.php"
            val MOST_VIEWED_URL = "mostview.php"
            val CATEGORY_DATA_URL = "category_data.php?id=%s"
            val SEARCH_DATA_URL = "search_data.php?sid=%s"

            val IMAGE_BASE_URL = "https://www.cricketpanchayat.com/admin/webroot/img/uploads/articles/"

        }
    }

    interface HttpMethods {
        companion object {
            val HTTP_GET = "GET"
        }
    }

}