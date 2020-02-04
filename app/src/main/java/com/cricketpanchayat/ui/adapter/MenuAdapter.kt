package com.cricketpanchayat.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cricketpanchayat.R
import com.cricketpanchayat.models.Menu

class MenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val selectedMenu: MutableLiveData<String> = MutableLiveData()
    private var mMenuList: MutableList<Menu>

    init {
        mMenuList = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_menu_item_layout, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mMenuList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val menu = mMenuList[position]

        val menuViewHolder = holder as MenuViewHolder

        menuViewHolder.lblCategory.text = menu.title
        holder.itemView.setOnClickListener({
            selectedMenu.value = menu.menuType.name
        })

        if(menu.menuType == Menu.MENUTYPE.ADD) {
            menuViewHolder.menuIcon.setImageResource(R.drawable.ic_add_black)

        } else if(menu.menuType == Menu.MENUTYPE.LANGUAGE) {
            menuViewHolder.menuIcon.setImageResource(R.drawable.ic_language_black)

        } else if(menu.menuType == Menu.MENUTYPE.SETTINGS) {
            menuViewHolder.menuIcon.setImageResource(R.drawable.ic_settings_black)
        }

    }

    fun setMenuList(menuList: ArrayList<Menu>) {
        mMenuList = menuList
        notifyDataSetChanged()
    }

    private inner class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var lblCategory: TextView
        var menuIcon: ImageView

        init {
            lblCategory = itemView.findViewById(R.id.lblCategory)
            menuIcon = itemView.findViewById(R.id.menuIcon)
            menuIcon.visibility = View.VISIBLE
        }
    }
}