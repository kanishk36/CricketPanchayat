package com.cricketpanchayat.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.cricketpanchayat.R
import com.cricketpanchayat.common.base.AbstractFragment
import com.cricketpanchayat.models.categorymenu.Category
import com.cricketpanchayat.models.home.Feed
import com.cricketpanchayat.ui.adapter.CategoryAdapter
import com.cricketpanchayat.ui.home.adapters.FeedsAdapter
import com.cricketpanchayat.utils.AppCache
import com.cricketpanchayat.utils.AppConstants
import com.cricketpanchayat.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class HomeFragment : AbstractFragment<HomeViewModel>(), View.OnClickListener, SearchView.OnQueryTextListener {

    private lateinit var feedsListView: RecyclerView
    private lateinit var btnLatestArticle: Button
    private lateinit var btnMostViewed: Button
    private lateinit var btnAudioArticle: Button
    private lateinit var lblNoData: TextView
    private lateinit var tabLayout: TabLayout

    private lateinit var mFeedsAdapter: FeedsAdapter

    override val viewModel: HomeViewModel
        get() = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFeedsAdapter = FeedsAdapter()

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedsListView = view.findViewById(R.id.feedsList)
        btnLatestArticle = view.findViewById(R.id.btnLatestArticle)
        btnMostViewed = view.findViewById(R.id.btnMostViewed)
        btnAudioArticle = view.findViewById(R.id.btnAudioArticle)
        lblNoData = view.findViewById(R.id.lblNoData)
        tabLayout = view.findViewById(R.id.tabLayout)

        feedsListView.adapter = mFeedsAdapter

        btnLatestArticle.isSelected = true

        btnLatestArticle.setOnClickListener(this)
        btnMostViewed.setOnClickListener(this)
        btnAudioArticle.setOnClickListener(this)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.fetchCategoryFeeds(tab!!.tag.toString())
//                resetButtonSelection()
            }


        })

        addTabs()

        registerObservers()

//        if(AppCache.INSTANCE.containsKey(AppConstants.LATEST_FEED_TAG)) {
//            val latestFeeds: ArrayList<Feed> = AppCache.INSTANCE.getValueOfAppCache(AppConstants.LATEST_FEED_TAG) as ArrayList<Feed>
//            setFeedsList(latestFeeds)
//        }

//        viewModel.fetchLatestFeeds(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.toolbar_search_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val searchViewMenuItem = menu?.findItem(R.id.action_search)

        val searchView = searchViewMenuItem?.actionView as SearchView

        val imgSearchView = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val imgCloseView = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        imgSearchView.setImageResource(R.drawable.ic_search)
        imgCloseView.setImageResource(R.drawable.ic_close)
        searchView.setOnQueryTextListener(this)

        super.onPrepareOptionsMenu(menu)
    }

    override fun onClick(view: View?) {

        val latestFeeds: ArrayList<Feed>
        lblNoData.visibility = View.GONE
        feedsListView.visibility = View.VISIBLE

        when(view?.id) {
            R.id.btnLatestArticle -> {
                btnLatestArticle.isSelected = true
                btnMostViewed.isSelected = false
                btnAudioArticle.isSelected = false

                if(AppCache.INSTANCE.containsKey(AppConstants.LATEST_FEED_TAG)) {
                    latestFeeds = AppCache.INSTANCE.getValueOfAppCache(AppConstants.LATEST_FEED_TAG) as ArrayList<Feed>
                    setFeedsList(latestFeeds)

                } else {
                    viewModel.fetchLatestFeeds(true)
                }
            }

            R.id.btnMostViewed -> {
                btnLatestArticle.isSelected = false
                btnMostViewed.isSelected = true
                btnAudioArticle.isSelected = false

                if(AppCache.INSTANCE.containsKey(AppConstants.MOST_VIEWED_TAG)) {
                    latestFeeds = AppCache.INSTANCE.getValueOfAppCache(AppConstants.MOST_VIEWED_TAG) as ArrayList<Feed>
                    setFeedsList(latestFeeds)

                } else {
                    setFeedsList(ArrayList())
                    viewModel.fetchMostViewedFeeds(true)
                }

            }

            R.id.btnAudioArticle -> {
                btnLatestArticle.isSelected = false
                btnMostViewed.isSelected = false
                btnAudioArticle.isSelected = true
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.fetchSearchData(query!!)
        resetButtonSelection()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun registerObservers() {

        viewModel.feedsData.observe(this, Observer<ArrayList<Feed>> {

            if(it.isEmpty()) {
                lblNoData.visibility = View.VISIBLE
                feedsListView.visibility = View.GONE

            } else {
                lblNoData.visibility = View.GONE
                feedsListView.visibility = View.VISIBLE
            }

            setFeedsList(it)

        })

        viewModel.showLoading().observe(this, Observer {
            showProgressDialog(getString(R.string.loading))
        })

        viewModel.dismissLoading().observe(this, Observer {
            hideProgressDialog()
        })

        mFeedsAdapter.descData.observe(this, Observer<Feed> { feed ->
            launchActivity(feed)
        })

//        CategoryAdapter.categoryData.observe(this, Observer<String> {
//            viewModel.fetchCategoryFeeds(it)
//            resetButtonSelection()
//        })

    }

    private fun launchActivity(feed: Feed) {
        val intent = Intent(activity, DescriptionActivity::class.java)
        intent.putExtra("Description", feed)
        startActivity(intent)
    }

    private fun resetButtonSelection() {
        btnLatestArticle.isSelected = false
        btnMostViewed.isSelected = false
        btnAudioArticle.isSelected = false
    }

    private fun setFeedsList(feedsList: ArrayList<Feed>) {
        mFeedsAdapter.setFeedsList(feedsList)
        feedsListView.scrollToPosition(0)
    }

    private fun addTabs() {
        if(AppCache.INSTANCE.containsKey(AppConstants.CATEGORY_LIST_TAG)) {
            val categoryList = AppCache.INSTANCE.getValueOfAppCache(AppConstants.CATEGORY_LIST_TAG) as ArrayList<Category>

            for (category in categoryList) {
                val tab = tabLayout.newTab()

                tab.text = category.title
                tab.tag = category.slug
                tabLayout.addTab(tab)
            }

            viewModel.fetchCategoryFeeds(categoryList[0].slug)
        }

    }
}