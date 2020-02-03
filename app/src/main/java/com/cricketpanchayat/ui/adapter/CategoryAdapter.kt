package com.cricketpanchayat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cricketpanchayat.R
import com.cricketpanchayat.models.categorymenu.Category

class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var mCategoryList: MutableList<Category>

    companion object {
        val categoryData: MutableLiveData<String> = MutableLiveData()
    }

    init {
        mCategoryList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_menu_item_layout, parent, false)
        return CategoryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = mCategoryList[position]

        (holder as CategoryViewHolder).lblCategory.text = category.title
        holder.itemView.setOnClickListener({
            categoryData.value = category.slug
        })
    }

    fun setCategoryList(categoryList: ArrayList<Category>) {
        mCategoryList = categoryList
        notifyDataSetChanged()
    }

    private inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var lblCategory: TextView

        init {
            lblCategory = itemView.findViewById(R.id.lblCategory)
        }
    }
}