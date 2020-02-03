package com.cricketpanchayat.ui.home

import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.cricketpanchayat.R
import com.cricketpanchayat.common.base.AbstractActivity
import com.cricketpanchayat.common.base.BaseViewModel
import com.cricketpanchayat.models.home.Feed
import com.cricketpanchayat.utils.AppConstants


class DescriptionActivity : AbstractActivity<BaseViewModel>() {

    override val viewModel: BaseViewModel
        get() = ViewModelProviders.of(this).get(BaseViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val feed = intent.getParcelableExtra<Feed>("Description")
        val imageUrl = AppConstants.ServiceURLs.IMAGE_BASE_URL.plus(feed.article_image)
//        val summary = "<html><body>%s</body></html>"

        supportActionBar?.title = feed.title

        val webView = findViewById<WebView>(R.id.contentDescription)

        findViewById<TextView>(R.id.descHeading).text = feed.title
        val imageView = findViewById<ImageView>(R.id.articleImage)

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)


        webView.loadData(feed.description, "text/html; charset=utf-8", "utf-8");
    }

}
