package com.cricketpanchayat

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cricketpanchayat.common.base.AbstractActivity
import com.cricketpanchayat.models.categorymenu.Category
import com.cricketpanchayat.utils.AppCache
import com.cricketpanchayat.utils.AppConstants
import com.cricketpanchayat.viewmodels.SplashViewModel

class SplashActivity : AbstractActivity<SplashViewModel>() {

    override val viewModel: SplashViewModel
        get() = ViewModelProviders.of(this).get(SplashViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.FullscreenTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        viewModel.categoryListData.observe(this, Observer<ArrayList<Category>> {

            AppCache.INSTANCE.addToAppCache(AppConstants.CATEGORY_LIST_TAG, it)
            launchMainActivity()

        })

        viewModel.errorResponse().observe(this, Observer {
            launchMainActivity()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCategoryList()
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
