package com.cricketpanchayat

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.cricketpanchayat.common.base.AbstractActivity
import com.cricketpanchayat.models.Menu
import com.cricketpanchayat.ui.adapter.CategoryAdapter
import com.cricketpanchayat.ui.adapter.MenuAdapter
import com.cricketpanchayat.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AbstractActivity<HomeViewModel>(), View.OnClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
//    private var categoryAdapter: CategoryAdapter? = null

    override val viewModel: HomeViewModel
        get() = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        navView = findViewById(R.id.nav_view)

        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener(this)

        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_history, R.id.navigation_add, R.id.navigation_settings, R.id.navigation_more
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

//        categoryAdapter = CategoryAdapter()

        val menuAdapter = getMenuAdapter()

        val menuListView = findViewById<RecyclerView>(R.id.menuListView)
        menuListView.adapter = menuAdapter

//        CategoryAdapter.categoryData.observe(this, Observer<String> {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        })

        menuAdapter.selectedMenu.observe(this, Observer {
            drawerLayout.closeDrawer(GravityCompat.START)
        })

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        NavigationUI.setupWithNavController(navView, navController)
//        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnMenu -> drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun getMenuAdapter(): MenuAdapter {

        val menuList = ArrayList<Menu>()
//        menuList.add(Menu(getString(R.string.title_add), Menu.MENUTYPE.ADD))
        menuList.add(Menu(getString(R.string.title_language), Menu.MENUTYPE.LANGUAGE))
        menuList.add(Menu(getString(R.string.title_settings), Menu.MENUTYPE.SETTINGS))

        val menuAdapter = MenuAdapter()
        menuAdapter.setMenuList(menuList)

        return menuAdapter
    }

}
