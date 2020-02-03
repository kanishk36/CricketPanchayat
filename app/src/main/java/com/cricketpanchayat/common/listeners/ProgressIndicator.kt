package com.cricketpanchayat.common.listeners

interface ProgressIndicator {

    fun onStartProgress()

    fun onUpdateProgress(percentage: Int)

    fun onEndProgress()
}