package com.chentong.firefighterapp.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chentong.firefighterapp.R
import com.chentong.firefighterapp.nav.NavFragment
import timber.log.Timber

class MyFragment : NavFragment() {
    companion object {
        @JvmStatic
        fun newInstance(): MyFragment{
            return MyFragment()
        }
        private const val PERMISSION_REQUEST_CODE = 42
    }
    override fun refresh() {
        Timber.d("Refreshing tab: My")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false)
    }
}