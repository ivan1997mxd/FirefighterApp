package com.chentong.firefighterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.chentong.firefighterapp.nav.NavPagerAdapter
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navBar:AHBottomNavigation
    private val tabHistory: Stack<Int> = Stack()
    private var saveTabHistory: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup navigation bar
        navBar = findViewById(R.id.navigation)
        navBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        // Load navigation items.
        val navBarAdapter = AHBottomNavigationAdapter(this, R.menu.navigation_items)
        navBarAdapter.setupWithBottomNavigation(navBar)

        // Setup tab history.
        val navPager = findViewById<ViewPager>(R.id.view_pager)
        navPager.offscreenPageLimit = 3
        navPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (saveTabHistory) {
                    tabHistory.push(position)
                }
            }
        })

        val navPagerAdapter = NavPagerAdapter(supportFragmentManager)
        navPager.adapter = navPagerAdapter
        navBar.setOnTabSelectedListener { position, wasSelected ->
            val curTab = navPagerAdapter.getCurrentFragment()
            if (wasSelected){
                curTab.refresh()
                false
            }else{
                if (position == 3){
                    showMenu()
                    false
                }else{
                    navPager.setCurrentItem(position, false)
                    true
                }
            }
        }
        tabHistory.push(0)
        saveTabHistory = true
//
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.title = getString(R.string.app_name)
//        setSupportActionBar(toolbar)
    }

    private fun showMenu(){
        Timber.d("Showing menu")

        val menuDialog = BottomSheetBuilder(this,R.style.AppTheme_BottomSheetDialog)
            .setMode(BottomSheetBuilder.MODE_LIST)
            .addItem(R.string.menu_settings, R.string.menu_settings, R.drawable.outline_settings_24)
            .addItem(R.string.menu_about, R.string.menu_about, R.drawable.outline_info_24)
            .addDividerItem()
            .addItem(R.string.menu_licenses, R.string.menu_licenses, null)
            .expandOnStart(true)
            .setItemClickListener {
                when (it.itemId) {
                    R.string.menu_about -> doAbout()
                    R.string.menu_settings -> doSettings()
                    R.string.menu_licenses -> doLicenses()
                }
            }
            .createDialog()
        menuDialog.show()
    }

    private fun doSettings() {
        Timber.d("Showing app settings")
    }

    private fun doAbout() {
        Timber.d("Displaying information about this app")

    }

    private fun getApplicationVersion(): String {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        return pInfo.versionName
    }

    private fun doLicenses() {
        Timber.d("Showing software licenses")

    }
}