package com.chentong.firefighterapp.nav

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chentong.firefighterapp.chat.ChatFragment
import com.chentong.firefighterapp.my.MyFragment
import com.chentong.firefighterapp.scan.ScanFragment


class NavPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    private val fragments = arrayListOf(
        ScanFragment.newInstance(),
        MyFragment.newInstance(),
        ChatFragment.newInstance()
    )

    private var currentFragment: NavFragment = fragments[0]

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): NavFragment {
        return fragments[position]
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (currentFragment != `object`){
            currentFragment = `object` as NavFragment
        }
        super.setPrimaryItem(container, position, `object`)
    }

    fun getCurrentFragment(): NavFragment{
        return currentFragment
    }
}