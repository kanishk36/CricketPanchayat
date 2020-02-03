package com.cricketpanchayat.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricketpanchayat.R
import com.cricketpanchayat.models.home.Feed
import com.cricketpanchayat.utils.AppConstants

class FeedsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mFeedsList: MutableList<Feed>
    var descData: MutableLiveData<Feed> = MutableLiveData()
    val requestOptions: RequestOptions = RequestOptions()

    init {
        mFeedsList = ArrayList()
        requestOptions.placeholder(R.drawable.image_placeholder)
        requestOptions.error(R.drawable.image_placeholder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feeds_list_item_layout, parent, false)
        return FeedsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val feedsViewHolder = holder as FeedsViewHolder
        val feed = mFeedsList[position]

        val imageUrl = AppConstants.ServiceURLs.IMAGE_BASE_URL.plus(feed.article_image)

        feedsViewHolder.lblArticleTitle.text = feed.title
        feedsViewHolder.lblArticleAuthor.text = holder.itemView.resources.getString(R.string.lblArticleAuthorBy)
                                                .plus(" ").plus(feed.author_name)

        feedsViewHolder.btnDescription.setOnClickListener( {
            descData.value = feed
        })

        feedsViewHolder.itemView.setOnClickListener( {
            descData.value = feed
        })



        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(feedsViewHolder.imgArticle)

    }

    fun setFeedsList(feedsList: ArrayList<Feed>) {
        mFeedsList = feedsList
        notifyDataSetChanged()
    }

    private inner class FeedsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var imgArticle: ImageView
        var lblArticleTitle: TextView
        var lblArticleAuthor: TextView
        var btnDescription: ImageButton

        init {
            imgArticle = itemView.findViewById(R.id.imgArticle)
            lblArticleTitle = itemView.findViewById(R.id.lblArticleTitle)
            lblArticleAuthor = itemView.findViewById(R.id.lblArticleAuthor)
            btnDescription = itemView.findViewById(R.id.btnDescription)
        }
    }
}