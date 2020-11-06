package com.chentong.firefighterapp.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chentong.firefighterapp.R
import com.chentong.firefighterapp.nav.NavFragment
import timber.log.Timber

class ChatFragment : NavFragment() {
    companion object {
        @JvmStatic
        fun newInstance(): ChatFragment{
            return ChatFragment()
        }
        private const val PERMISSION_REQUEST_CODE = 42
    }
    override fun refresh() {
        Timber.d("Refreshing tab: Chat")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
}